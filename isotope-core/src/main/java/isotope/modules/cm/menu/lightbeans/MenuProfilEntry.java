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

import isotope.modules.cm.menu.constant.MenuEntryType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Représente le retour du controller au front office (mix entre menu et profil accessible à l'utilisateur)
 * Created by bbauduin on 23/12/2016.
 */
public class MenuProfilEntry extends MenuEntryLightBean {

	private int ordre;

	private Map<Long, MenuProfilEntry> children;

	public MenuProfilEntry(Long id, String code , Long idFunction, Long idParent, MenuEntryType type, int ordre, String icon, String url, String urlFunction) {
		this.setId(id);
		this.setCode(code);
		this.setIdFunction(idFunction);
		this.setIdParent(idParent);
		this.setType(type);
		this.setIcon(icon);
		if (type == MenuEntryType.FUNCTION) {
			this.setUrl(urlFunction);
		} else {
			this.setUrl(url);
		}
		this.ordre = ordre;
	}

	public Map<Long, MenuProfilEntry> getChildren() {
		return children;
	}

	public void addChildren(MenuProfilEntry child) {
		if (children == null) children = new LinkedHashMap<>();
		if (child != null) {
			children.put(child.getId(), child);
		}
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
}
