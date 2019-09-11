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

package isotope.modules.cm.menu.lightbeans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import isotope.modules.cm.menu.constant.MenuEntryType;

public class MenuEntryLightBean {
	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
	private Long id;

	private String code;

	private MenuEntryType type;

	private String icon;

	private String url;

	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
	private Long idFunction;

	@JsonSerialize(
			using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
	)
	private Long idParent;

	public MenuEntryLightBean() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MenuEntryType getType() {
		return this.type;
	}

	public void setType(MenuEntryType type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getIdFunction() {
		return this.idFunction;
	}

	public void setIdFunction(Long idFunction) {
		this.idFunction = idFunction;
	}

	public Long getIdParent() {
		return this.idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}

}
