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

package isotope.modules.mail.helper;

import isotope.modules.mail.service.ITemplateService;

import javax.activation.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * Builder permettant de construire un objet EmailSpec
 */
public class EmailSpecBuilder {

	private static final String DEFAULT_MEDIA_TYPE = "application/octet";

	private ITemplateService templateService;

	private String fromAddress;
	private String replyToAddress;
	private String subject;
	private String body;
	private String template;
	private Map<String, Object> contextObjects;
	private final List<MailRecipientSpec> recipients = new ArrayList<>();
	private final List<MailAttachementSpec> attachements = new ArrayList<>();
	private boolean deferred;

	/**
	 * Constructeur permettant l'injection du templateService
	 */
	private EmailSpecBuilder(ITemplateService templateService) {
		this.templateService = templateService;
	}

	public static EmailSpecBuilder create(ITemplateService templateService) {
		return new EmailSpecBuilder(templateService);
	}

	public EmailSpecBuilder from(String fromAddress) {
		this.fromAddress = fromAddress;
		return this;
	}

	public EmailSpecBuilder replyTo(String replyToAddress) {
		this.replyToAddress = replyToAddress;
		return this;
	}

	public EmailSpecBuilder subject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * <p>
	 * Indique le corps statique du mail.
	 * </p>
	 *
	 * <p>
	 * Mutuellement exclusive avec {@link #withTemplate(String, Map)}.
	 * </p>
	 *
	 * @param body le corps du mail
	 * @return le builder
	 */
	public EmailSpecBuilder body(String body) {
		this.body = body;
		this.template = null;
		this.contextObjects = null;
		return this;
	}

	/**
	 * Indique si l'envvoi de mail sera fait de manière synchrone ou non
	 *
	 * @param deferred
	 * @return
	 */
	public EmailSpecBuilder deferred(boolean deferred) {
		this.deferred = deferred;
		return this;
	}

	public EmailSpecBuilder withTemplate(String templateName) {
		return withTemplate(templateName, Collections.emptyMap());
	}

	/**
	 * <p>
	 * Indique le nom du template et le modèle à utiliser pour le corps du mail.
	 * </p>
	 *
	 * <p>
	 * Mutuellement exclusive avec {@link #body}.
	 * </p>
	 *
	 * @param templateName le nom du template
	 * @param objectMap    le modèle
	 * @return le builder
	 */
	public EmailSpecBuilder withTemplate(String templateName, Map<String, Object> objectMap) {
		this.template = templateName;
		this.contextObjects = new HashMap<>(objectMap);
		this.body = null;
		return this;
	}

	public synchronized EmailSpecBuilder recipients(List<MailRecipientSpec> recipients) {
		this.recipients.clear();
		this.recipients.addAll(recipients);
		return this;
	}

	public EmailSpecBuilder addAttachment(String name, DataSource content) {
		return this.addAttachment(name, content, DEFAULT_MEDIA_TYPE);
	}

	public EmailSpecBuilder addAttachment(String name, DataSource content, String mimeType) {
		this.attachements.add(new MailAttachementSpec(name, content, mimeType, false));
		return this;
	}

	public EmailSpecBuilder addInlineAttachment(String contentId, DataSource content, String mimeType) {
		this.attachements.add(new MailAttachementSpec(contentId, content, mimeType, true));
		return this;
	}

	public EmailSpecBuilder clearAttachments() {
		this.attachements.clear();
		return this;
	}

	/**
	 * <p>
	 * Construit une nouvelle instance de {@link EmailSpec} à partir des données transmises au builder.
	 * </p>
	 *
	 * <p>
	 * Pour le corps du mail, la priorité est donnée au contenu statique ({@link #body}) puis au contenu dynamique
	 * ({@link #withTemplate(String, Map)}).
	 * </p>
	 *
	 * @return une nouvelle instance de {@link EmailSpec} correspondant aux informations contenues dans le builder
	 * @throws IOException si une erreur est survenue lors de l'évaluation du template
	 */
	public EmailSpec build() throws IOException {
		String content;
		if (body != null) {
			content = body;
		} else {
			content = templateService.mergeTemplateIntoString(template, contextObjects, null);
		}
		return new EmailSpec(fromAddress, replyToAddress, subject, content, recipients, attachements, deferred);
	}
}
