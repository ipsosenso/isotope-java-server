/*
 * Isotope 1.6
 * Copyright (C) 2019 IpsoSenso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package isotope.modules.mail.service;

import isotope.commons.services.CrudEntityService;
import isotope.modules.mail.config.MailModuleConfiguration;
import isotope.modules.mail.helper.EmailSpec;
import isotope.modules.mail.helper.EmailSpecBuilder;
import isotope.modules.mail.helper.MailAttachementSpec;
import isotope.modules.mail.helper.MailRecipientSpec;
import isotope.modules.mail.model.MAIL_STATUS;
import isotope.modules.mail.model.Mail;
import isotope.modules.mail.model.MailAttachement;
import isotope.modules.mail.model.MailRecipient;
import isotope.modules.mail.repository.MailRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service in charge of mail management store to database,
 *
 * Created by oturpin on 20/12/16.
 */
@Service
public class MailService extends CrudEntityService<MailRepository, Mail, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private ITemplateService templateService;

    private JavaMailSender mailSender;

    private MailModuleConfiguration mailModuleConfiguration;

    /**
     * Constructeur du service */
    public MailService(final MailRepository repository, final ITemplateService templateService, JavaMailSender mailSender, MailModuleConfiguration mailModuleConfiguration) {
        super(repository);
        this.templateService = templateService;
        this.mailSender = mailSender;
        this.mailModuleConfiguration = mailModuleConfiguration;
    }

    /**
     * Return a new mail builder
     * @return
     */
    public EmailSpecBuilder createBuilder(){
        return EmailSpecBuilder.create(templateService);
    }

    /**
     *
     * @param emailSpec
     * @throws IOException
     */
    public void sendMail(EmailSpec emailSpec) throws IOException {
        Mail mail = this.saveMail(emailSpec, emailSpec.hashAttachments());
        if(!emailSpec.isDeferred()) {
            // send synchronously
            sendMail(mail);
        }
    }

    /**
     * @param emailSpec
     */
    @Transactional
    public Mail saveMail(EmailSpec emailSpec, boolean isMultipart) throws IOException {
        // check constraints
        LOGGER.info("Sendmail "+emailSpec);

        Mail mail = new Mail();
        mail.setCreationDate(LocalDateTime.now());

        mail.setSubject( emailSpec.getSubject());
        mail.setMailFrom(emailSpec.getFromAddress());
        mail.setBody( emailSpec.getBody());
        mail.setPriority(0);

        // create associated recipients
        for(MailRecipientSpec mailRecipientSpec : emailSpec.getRecipients()){
            MailRecipient recipient = new MailRecipient();
            recipient.setType( mailRecipientSpec.getType().name());
            recipient.setMailTo(mailRecipientSpec.getMail());
            mail.addRecipient(recipient);
        }

        // Attachements
        for (MailAttachementSpec attSpec : emailSpec.getAttachements()) {
            MailAttachement att = new MailAttachement();
            att.setAttachementName(attSpec.getAttachementName());
            att.setContent(IOUtils.toByteArray(attSpec.getContent().getInputStream()));
            att.setMimeType(attSpec.getMimeType());
            att.setInline(attSpec.isInline());
            mail.addAttachements(att);
        }

        if(emailSpec.isDeferred()) {
            // change status so it will be picked up by the next execution of the sendMailTask
            mail.setStatus(MAIL_STATUS.WAITING.name());
        }else{
            /*
             * it's important to save the mail before sending it (even for a sync one) because the
             * mail could be sent without being serializable (database constraint failed for instance)
             */
            mail.setStatus(MAIL_STATUS.SENDING.name());
        }
        return repository.save(mail);
    }

    /**
     * Get stream of mails waiting to be processed
     *
     * @return
     */
    @Transactional
    public Stream<Mail> getStreamOfWaitingEmails() {
        return repository.streamAllEntities(LocalDateTime.now(), MAIL_STATUS.WAITING.name());
    }

    /** Change status of the mail to SENT */
    private void markAsSent(Mail mail) {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Mail marked as sent");
        }
        mail.setStatus(MAIL_STATUS.SENT.name());
    }

    /**
     * Prepare a mail with the content of object stored in database
     *
     * @param mail the mail to be processed
     */
    public void sendMail(Mail mail){

        // on commence par préparer la liste des destinataires :
        Set<MailRecipient> recipients = filterMails(mail.getRecipients(), mailModuleConfiguration.getFilter());

        try {
            // on n'envoi le mail que si il y a au moins 1 recipient de type TO actif
            if(mailShouldBeSent(recipients)){
                MimeMessagePreparator messagePreparator = mimeMessage -> {
                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, !mail.getAttachements().isEmpty());
                    messageHelper.setFrom(mail.getMailFrom());

                    recipients.forEach( r -> {
                        try {
                            if(r.getType().equals(MailRecipientSpec.TYPE.TO.name())) {
                                messageHelper.addTo(r.getMailTo());
                            }else if(r.getType().equals(MailRecipientSpec.TYPE.CC.name())) {
                                messageHelper.addCc(r.getMailTo());
                            }else{
                                messageHelper.addBcc(r.getMailTo());
                            }
                        } catch (MessagingException e) {
                            LOGGER.error("Une erreur est survenue lors de l'envoi du mail {} id : {} ",mail.getSubject(), mail.getId(), e);
                        }
                    });

                    messageHelper.setSubject(mail.getSubject());
                    messageHelper.setText(mail.getBody(), true);
                    // On ajoute des pièces-jointes
                    for (MailAttachement attachement : mail.getAttachements()) {
                        if (attachement.isInline()) {
                            messageHelper.addInline(attachement.getAttachementName(), new ByteArrayResource(attachement.getContent()), attachement.getMimeType());
                        } else {
                            messageHelper.addAttachment(attachement.getAttachementName(), new ByteArrayResource(attachement.getContent()), attachement.getMimeType());
                        }
                    }
                };
                // Send the mail effectively
                try {
                    mailSender.send(messagePreparator);

                    // mark as read (change status)
                    markAsSent(mail);
                } catch (MailPreparationException | MailParseException e) {
                    // irrecoverable error
                    mail.setStatus(MAIL_STATUS.IRRECOVERABLE_ERROR.name());
                    LOGGER.error("Une erreur est survenue lors de l'envoi du mail {} id : {} ",mail.getSubject(), mail.getId(), e);
                } catch (MailException e) {
                    LOGGER.error("Une erreur est survenue lors de l'envoi du mail {} id : {} ",mail.getSubject(), mail.getId(), e);
                    // change status so it won't be processed the next time
                    mail.setStatus(MAIL_STATUS.ERROR.name());
                }
            }else{
                // Send the mail effectively
                markAsSent(mail);
                LOGGER.info("Aucun mail n'a été envoyé (filtrage mail actif) {} id : {} ",mail.getSubject(), mail.getId());
            }
        } finally {
            // non-blocking save
            try {
                repository.save(mail);
            } catch (Throwable e) {
                LOGGER.error("Une erreur est survenue lors de la sauvegarde du mail, id {}", mail.getId(), e);
            }
        }
    }

    /**
     * Méthode permettant de vérifier si au moins 1 récipiendaire est valide au sein d'une collection
     * @param recipients la liste des recipiendaires
     * @return
     */
    public boolean mailShouldBeSent(Set<MailRecipient> recipients){
        return recipients != null && recipients.stream().filter(r -> r.getType().equals(MailRecipientSpec.TYPE.TO.name())).count()>0;
    }

    /**
     * Filtrer une collection de récipiendaires en fonction d'un expression régulière
     *
     * @param recipientList la liste des récipiendaires
     * @param filterExpression expression régulière
     * @return
     */
    public Set<MailRecipient> filterMails(Set<MailRecipient> recipientList, String filterExpression) {
        if (recipientList != null && filterExpression!= null && !filterExpression.isEmpty()) {
            return recipientList.stream().filter(mailRecipient -> {
                String[] filters = filterExpression.split(",");
                for (String filter : filters) {
                    if (mailRecipient.getMailTo() != null && filter != null && mailRecipient.getMailTo().endsWith(filter.trim())) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toSet());
        } else {
            return recipientList;
        }
    }


    /**
     * Suppression des mails dont l'âge est supèrieur à maxRetentionBeforeDelete
     *
     * @param maxRetentionBeforeDelete
     */
    public int deleteMailOlderThan(Integer maxRetentionBeforeDelete) {

        LocalDateTime maxDate =
                LocalDateTime
                        .now()
                        .with(LocalTime.MIN)
                        .minusDays(maxRetentionBeforeDelete);

        return repository.deleteByCreationDateBefore(maxDate);
    }
}
