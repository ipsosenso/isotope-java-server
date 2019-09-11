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

import java.util.Collections;
import java.util.List;

/**
 * Bean expressing Mail Specification that is
 *
 * - who sends the mail
 * - who will receive it (via MailRecipientSpec)
 * - body, subjects ...
 *
 * Created by oturpin on 20/12/16.
 */
public class EmailSpec {

	private final String fromAddress;
	private final String replyToAddress;
	private final String subject;
	private final String body;
	private final List<MailRecipientSpec> recipients;
	private final List<MailAttachementSpec> attachements;

	/**
	 * Flag indiquant si le mail créé doit être envoyé immédiatement par défaut le mail est envoyé avec un délai
	 */
	private boolean deferred = true;

	/**
	 * Constructeur
	 *
	 * @param fromAddress
	 * @param replyToAddress
	 * @param subject
	 * @param body
	 * @param recipients
	 * @param attachements
	 * @param deferred
	 */
	EmailSpec(String fromAddress,
	          String replyToAddress,
	          String subject,
	          String body,
	          List<MailRecipientSpec> recipients,
	          List<MailAttachementSpec> attachements,
	          boolean deferred) {
		this.fromAddress = fromAddress;
		this.replyToAddress = replyToAddress;
		this.subject = subject;
		this.body = body;
		this.recipients = Collections.unmodifiableList(recipients);
		this.attachements = Collections.unmodifiableList(attachements);
		this.deferred = deferred;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getReplyToAddress() {
		return replyToAddress;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public boolean isDeferred() {
		return deferred;
	}

	public List<MailRecipientSpec> getRecipients() {
		return recipients;
	}

	public List<MailAttachementSpec> getAttachements() {
		return attachements;
	}

	public boolean hashAttachments() {
		return attachements != null
				&& !getAttachements().isEmpty();
	}

	@Override
	public String toString() {
		return
				"EmailSpec{" +
						"from='" + fromAddress + '\'' +
						", subject='" + subject + '\'' +
						", hasAttachements=" + hashAttachments() +
						", deferred=" + deferred +
						'}';
	}
}
