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

/**
 * Bean definition for mail recipients
 * @see <a href="https://tools.ietf.org/html/rfc4021">https://tools.ietf.org/html/rfc4021</a> for further details
 *
 * Created by oturpin on 20/12/16.
 */
public class MailRecipientSpec {

    /** Mailbox */
    private String mail;

    /** Type of recipient */
    private TYPE type;

    /** Constructor */
    public MailRecipientSpec(String mail, TYPE type) {
        this.mail = mail;
        this.type = type;
    }

    /**
     * Type of recipient
     * <ul>
     *     <li>TO : primary recipient mailbox</li>
     *     <li>BCC : blind carbon copy</li>
     *     <li>CC : carbon copy</li>
     * </ul>
     */
    public enum TYPE{
        TO, CC, BCC
    }

    public String getMail() {
        return mail;
    }

    public TYPE getType() {
        return type;
    }
}
