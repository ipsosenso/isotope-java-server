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
package isotope.modules.mail.tasks;

import isotope.modules.mail.config.MailModuleConfiguration;
import isotope.modules.mail.model.Mail;
import isotope.modules.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Task that will periodically read mail from database and send them thought mail host
 *
 * Created by oturpin on 20/12/16.
 */
@Component
public class SendmailTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendmailTask.class);

    private MailService mailService;
    private MailModuleConfiguration moduleConfig;


    /**
     * Constructor used by dependency injection system
     * @param mailService
     */
    public SendmailTask(MailService mailService, MailModuleConfiguration config){
        this.mailService = mailService;
        this.moduleConfig = config;
    }

    @Scheduled(cron = "${isotope.mail.send-mail-cron-expression}")
    @Transactional
    public void sendMail(){

        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Check pending mail (if any)");
        }

        // iterate through stream of mails stored in the database
        try(Stream<Mail> streamOfMail = mailService.getStreamOfWaitingEmails()){
            streamOfMail
                    .filter( shouldFilterMail() )
                    .forEach(mail -> {

                        if(LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Will sendMail " + mail);
                        }

                        // try to send mail
                        mailService.sendMail(mail);
                    });
        }


    }

    @Scheduled(cron = "${isotope.mail.delete-mail-cron-expression}")
    @Transactional
    public void deleteMail(){
        if(moduleConfig.getMaxRetentionBeforeDelete()!=null){
            int done = mailService.deleteMailOlderThan(moduleConfig.getMaxRetentionBeforeDelete());
            LOGGER.info("{} mail deleted from database", done);
        }
    }



    public  Predicate<Mail> shouldFilterMail(){
        return mail -> (moduleConfig.getFilter()==null || moduleConfig.getFilter().isEmpty()) || (mail.getMailFrom().length()>0);
    }


}
