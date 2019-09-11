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

package isotope.modules.user.controllers.validators;


import isotope.modules.user.lightbeans.UserLightbean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator qui permet de vérifier que le mot de passe et sa confirmation correspondent
 */
@Component
public class PasswordValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return UserLightbean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLightbean userLightbean = (UserLightbean) target;
        if (userLightbean.getPassword() != null && !userLightbean.getPassword().equals(userLightbean.getPasswordRepeated())) {
            errors.rejectValue("password", "password.errors.no_match");
        }
    }
}
