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

package isotope.modules.cm.menu.controllers;

import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.service.MenuService;
import isotope.modules.cm.menu.service.impl.MenuServiceImpl;
import isotope.modules.user.IJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Permet de récupérer les informations sur le menu pour un user
 *
 * Created by bbauduin on 23/12/2016.
 */
@RestController
@RequestMapping("/api/admin/menu")
public class MenuController {

	@Autowired
	MenuService menuService;

	/**
	 * Retourne le menu demandé sous la forme d'une hashmap
	 *
	 * @param user
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/{code}", method = RequestMethod.GET)
	public Map<Long, MenuProfilEntry> getMenus(@AuthenticationPrincipal IJwtUser user, @PathVariable String code) {
		return menuService.getMenus(user, code);
	}

}
