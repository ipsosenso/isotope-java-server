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

package isotope.modules.mail.model;

import isotope.commons.entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(
		name = "is_mail"
)
public class Mail extends BaseEntity {
	/**
	 * Sender of the mail
	 */
	@Column(
			name = "mail_from",
			nullable = false,
			length = 255
	)
	private String mailFrom;

	/**
	 * Reply to sender
	 */
	@Column(
			name = "mail_reply_to",
			length = 255
	)
	private String mailReplyTo;

	/**
	 * Subject of the mail
	 */
	@Column(
			name = "subject",
			nullable = false,
			length = 255
	)
	private String subject;

	/**
	 * Body of this mail
	 */
	@Column(
			name = "body",
			nullable = false,
			length = 16777215
	)
	private String body;

	/**
	 * Creation date
	 */
	@Column(
			name = "creation_date",
			nullable = false,
			updatable = false
	)
	private LocalDateTime creationDate;

	/**
	 * Priority in submission
	 */
	@Column(
			name = "priority",
			nullable = false
	)
	private Integer priority;

	/**
	 * Delivery status
	 */
	@Column(
			name = "status",
			nullable = false,
			length = 10
	)
	private String status;

	@OneToMany(mappedBy = "mail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final Set<MailRecipient> recipients = new HashSet<MailRecipient>();

	@OneToMany(mappedBy = "mail", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private Set<MailAttachement> attachements = new HashSet<>();

	public Mail() {
	}

	@Override
	public String toString() {
		return "Mail{" +
				"mailFrom='" + mailFrom + '\'' +
				", mailReplyTo='" + mailReplyTo + '\'' +
				", subject='" + subject + '\'' +
				", body='" + body + '\'' +
				", creationDate=" + creationDate +
				", priority=" + priority +
				", status='" + status + '\'' +
				'}';
	}

	public String getMailFrom() {
		return this.mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public Optional<String> getMailReplyTo() {
		return Optional.ofNullable(this.mailReplyTo);
	}

	public void setMailReplyTo(String mailReplyTo) {
		this.mailReplyTo = mailReplyTo;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<MailRecipient> getRecipients() {
		return Collections.unmodifiableSet(this.recipients);
	}

	public void addRecipient(MailRecipient mailRecipient) {
		mailRecipient.setMail(this);
		this.recipients.add(mailRecipient);
	}

	public Set<MailAttachement> getAttachements() {
		return attachements;
	}

	public void addAttachements(MailAttachement attachement) {
		attachement.setMail(this);
		this.attachements.add(attachement);
	}
}
