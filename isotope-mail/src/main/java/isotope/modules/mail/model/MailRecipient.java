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
		name = "is_mail_recipient"
)
public class MailRecipient extends BaseEntity {

	/**
	 * Recipient */
	@Column(
			name = "mail_to",
			nullable = false,
			length = 255
	)
	private String mailTo;

	/**
	 * Recipient type */
	@Column(
			name = "type",
			nullable = false,
			length = 3
	)
	private String type;




  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_mail", nullable = false, referencedColumnName = "id")
  private Mail mail;

	public MailRecipient() {
	}


	public String getMailTo() {
		return this.mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

  public Mail getMail() {
    return mail;
  }

  public void setMail(Mail mail) {
    this.mail = mail;
  }
}
