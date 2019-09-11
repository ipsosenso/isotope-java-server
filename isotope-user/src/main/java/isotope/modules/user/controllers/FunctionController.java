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

package isotope.modules.user.controllers;

import isotope.modules.user.IJwtUser;
import isotope.modules.user.controllers.beans.Path;
import isotope.modules.user.lightbeans.FunctionLightBean;
import isotope.modules.user.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ce controller permet d'intéragir avec les fonctions de l'application
 *
 * Created by bbauduin on 21/12/2016.
 */
@RestController
@RequestMapping("/api/admin/function")
public class FunctionController {

    @Autowired
    FunctionService functionService;

    /**
     * Indique si un path donné est autorisé (s'il correspond à une fonction connue)
     */
    @RequestMapping(method = RequestMethod.POST)
    public boolean isAuthorized(@AuthenticationPrincipal IJwtUser user, @RequestBody Path path) {
        return functionService.isAuthorized(user, path.getPath());
    }

    /**
     * @param user
     * @return la liste de toutes les fonctions connues du service
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<FunctionLightBean> getFunctions(@AuthenticationPrincipal IJwtUser user) {
        return functionService.getFunctions();
    }

}
