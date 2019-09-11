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

package isotope.modules.cm.menu.service;

import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.user.IJwtUser;

import java.util.Map;

/**
 * Service permettant de manipuler les objets Menu profilés
 *
 * Created by oturpin on 18/04/17.
 */
public interface MenuService {

    /**
     * Retourne la liste des menus qu'un utilisateur a le droit de voir par rapport à un type
     *
     * @param user
     * @param code
     * @return
     */
    Map<Long, MenuProfilEntry> getMenus(IJwtUser user, String code);
}
