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
import isotope.modules.user.model.User;
import isotope.modules.user.repository.RoleRepository;
import isotope.modules.user.repository.UserRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation par défaut du service {@link UserService}
 * Created by oturpin on 15/06/16.
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(){}

    @Override
    public Optional<UserLightbean> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id)).map(LightBeanToBeanUtils::copyFrom);
    }

    @Override
    public Optional<UserLightbean> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email).map(LightBeanToBeanUtils::copyFrom);
    }

    @Override
    public Optional<UserLightbean> getOtherUserByEmail(Long id, String email) {
        return userRepository.findOneByIdNotAndEmail(id, email).map(LightBeanToBeanUtils::copyFrom);
    }

    @Override
    public Optional<UserLightbean> getUserByLogin(String login) {
        return userRepository.findOneByLogin(login).map(LightBeanToBeanUtils::copyFrom);
    }

    @Override
    public Optional<UserLightbean> getOtherUserByLogin(Long id, String login) {
        return userRepository.findOneByIdNotAndLogin(id, login).map(LightBeanToBeanUtils::copyFrom);
    }

    @Override
    public Collection<UserLightbean> getAllUsers(boolean showDisabled) {
        Collection<User> users;
        if (showDisabled) {
            users = userRepository.findAllByOrderByEmail();
        } else {
            users = userRepository.findByDisabledFalseOrderByEmail();
        }
        return users.stream().map(LightBeanToBeanUtils::copyFrom).collect(Collectors.toList());
    }

    @Override
    public UserLightbean create(Long idUser, UserLightbean userLightbean) {
        //Je crée un utilisateur...

        User user = new User();

        //En création, je mets à jour les dates de création et l'utilisateur
        user.setDateCreation(LocalDateTime.now());
        user.setIdUserCreation(idUser);

        //Je finis par sauvegarder mon utilisateur et je le retourne
        return maj(idUser, userLightbean, user);
    }

    @Override
    public boolean delete(Long idUser, Long idUserDelete) {
        //Je récupère l'utilisateur avec son id
        User user = userRepository.findOne(idUserDelete);

        //On ne supprime jamais un utilisateur, on le désactive
        user.setDisabled(true);

        //Je sauvegarde la modification
        userRepository.save(user);

        return true;
    }

    /**
     * Restaure un utilisateur supprimé
     * @param idUser l'utilisateur effectuant la modification
     * @param idUserRestore l'utilisateur à restaurer
     * @return l'utilisateur modifié
     */
    @Override
    public UserLightbean restore(Long idUser, Long idUserRestore) {
        //Je récupère l'utilisateur avec son id
        User user = userRepository.findOne(idUserRestore);

        //Je restaure l'utilisateur
        user.setDisabled(false);

        //Je sauvegarde la modification
        userRepository.save(user);

        return LightBeanToBeanUtils.copyFrom(user);
    }

    @Override
    public UserLightbean save(Long idUser, UserLightbean userLightbean) {
        //Je récupère l'utilisateur avec son id
        User user = userRepository.findOne(userLightbean.getId());

        //Je finis par sauvegarder mon utilisateur et je le retourne
        return maj(idUser, userLightbean, user);
    }

    /**
     * @return retourne un userLightbean mis à jour avec les infos du user
     */
    private UserLightbean maj(Long idUser, UserLightbean userLightbean, User user) {
        //... j'affecte toutes les propriétés de mon form
        user.setFirstname(userLightbean.getFirstname());
        user.setLastname(userLightbean.getLastname());
        user.setLogin(userLightbean.getLogin());
        user.setEmail(userLightbean.getEmail());
        user.setPhoneNumber(userLightbean.getPhoneNumber());
        user.setCompany(userLightbean.getCompany());
        user.setRoles(userLightbean.getRoles().stream().map(roleLightBean -> roleRepository.findOne(roleLightBean.getId())).collect(Collectors.toSet()));

        //Si un mot de passe a été fourni, je le mets à jour
        if (userLightbean.getPassword() != null) {
            //J'encode le mot de passe
            user.setPasswordHash(passwordEncoder.encode(userLightbean.getPassword()));
        }

        user.setDateModification(LocalDateTime.now());
        user.setIdUserModification(idUser);

        return LightBeanToBeanUtils.copyFrom(userRepository.save(user));
    }
}
