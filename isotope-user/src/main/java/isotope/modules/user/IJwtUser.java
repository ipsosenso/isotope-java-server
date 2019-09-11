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

package isotope.modules.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;

/**
 * Interface qui représente un user JWT d'isotope (relié au bean {{@link isotope.modules.user.model.User}})
 *
 * Created by bbauduin on 02/11/2016.
 */
public interface IJwtUser extends UserDetails {

    /**
     * Id utilisé pour retrouver le user en base
     * @return l'id de l'utilisateur isotope
     */
    Long getId();
    String getEmail();
    String getFirstname();
    String getLastname();
    Locale getLocale();

}
