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

@Entity
@Table(
		name = "is_mail_attachement"
)
public class MailAttachement extends BaseEntity {
	/**
	 * Id of the mail
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "id_mail",
			nullable = false,
			referencedColumnName = "id"
	)
	private Mail mail;

	/**
	 * Name of the attachement
	 */
	@Column(
			name = "attachement_name",
			nullable = false,
			length = 255
	)
	private String attachementName;

	/**
	 * Content of the mail (Render already don)
	 */
	@Column(
			name = "content",
			nullable = false
	)
	private byte[] content;

	/**
	 * Mime type for this attachement
	 */
	@Column(
			name = "mime_type",
			nullable = false,
			length = 45
	)
	private String mimeType;

	@Column(
			name = "inline",
			nullable = false
	)
	private boolean inline;

	public MailAttachement() {
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getAttachementName() {
		return this.attachementName;
	}

	public void setAttachementName(String attachementName) {
		this.attachementName = attachementName;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}
}
