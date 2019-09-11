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

import javax.activation.DataSource;

/**
 * Created by oturpin on 20/12/16.
 */
public class MailAttachementSpec {

	private final DataSource content;
	private final String attachementName;
	private final String mimeType;
	private final boolean inline;

	MailAttachementSpec(String name, DataSource content, String mimeType, boolean inline) {
		this.attachementName = name;
		this.content = content;
		this.mimeType = mimeType;
		this.inline = inline;
	}

	public DataSource getContent() {
		return content;
	}

	public String getAttachementName() {
		return attachementName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public boolean isInline() {
		return inline;
	}
}
