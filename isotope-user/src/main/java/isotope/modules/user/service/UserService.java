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

package isotope.modules.user.service;


import isotope.modules.user.lightbeans.UserLightbean;

import java.util.Collection;
import java.util.Optional;

/**
 * Service de modification des user isotope
 *
 * Created by spigot on 15/06/16.
 */
public interface UserService {
    Optional<UserLightbean> getUserById(Long id);

    Optional<UserLightbean> getUserByEmail(String email);

    Optional<UserLightbean> getOtherUserByEmail(Long id, String email);

    Optional<UserLightbean> getUserByLogin(String login);

    Optional<UserLightbean> getOtherUserByLogin(Long id, String login);

    Collection<UserLightbean> getAllUsers(boolean showDisabled);

    UserLightbean create(Long idUser, UserLightbean userLightbean);

    boolean delete(Long idUser, Long idUserDelete);

    UserLightbean restore(Long idUser, Long idUserRestore);

    UserLightbean save(Long idUser, UserLightbean userLightbean);

}
