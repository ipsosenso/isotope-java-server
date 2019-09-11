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


import isotope.commons.validation.ValidationErrorBuilder;
import isotope.modules.user.controllers.forms.UserForm;
import isotope.modules.user.controllers.validators.PasswordValidator;
import isotope.modules.user.controllers.validators.UserValidator;
import isotope.modules.user.lightbeans.UserLightbean;
import isotope.modules.user.service.UserService;
import isotope.modules.user.IJwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

/**
 * Controller permettant de récupérer les informations sur les utilisateurs
 *
 * Created by spigot on 20/05/2016.
 */
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(passwordValidator);
        binder.addValidators(userValidator);
    }

    /**
     * Renvoie la liste des utilisateurs
     * @param showDisabled true si on souhaite aussi récupérer les utilisateurs désactivés
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<UserLightbean> getUsersList(@RequestParam(value = "showDisabled", defaultValue = "false") boolean showDisabled){
        return userService.getAllUsers(showDisabled);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public Optional<UserLightbean> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity saveUser(@AuthenticationPrincipal IJwtUser user, @RequestBody @Valid UserLightbean userForm, BindingResult result) {

        //Si j'ai des erreurs, je les renvoie au front
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }

        //Si tout est ok, je sauvegarde l'utilisateur et le renvoie au front
        return ResponseEntity.ok(userService.save(user.getId(), userForm));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal IJwtUser user, @PathVariable Long id) {
        //Si tout est ok, je sauvegarde l'utilisateur et le renvoie au front
        return ResponseEntity.ok(userService.delete(user.getId(), id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@AuthenticationPrincipal IJwtUser user, @RequestBody @Valid UserForm userForm, BindingResult result) {

        //Si j'ai des erreurs, je les renvoie au front
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }

        //Si tout est ok, je crée l'utilisateur et le renvoie au front
        return ResponseEntity.ok(userService.create(user.getId(), userForm));
    }

    @RequestMapping(value = "{id}/restore", method = RequestMethod.POST)
    public ResponseEntity restoreUser(@AuthenticationPrincipal IJwtUser user, @PathVariable Long id) {
        return ResponseEntity.ok(userService.restore(user.getId(), id));
    }

}
