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

package isotope.modules.user.repository;

import isotope.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Ce repository nous permet de récupérer les infos de la base utilisateurs
 *
 * Created by qletel on 18/05/2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Récupère les utilisateurs non supprimés
     */
    Collection<User> findByDisabledFalseOrderByEmail();

    /**
     * Recupère tous les utilisateurs
     */
    Collection<User> findAllByOrderByEmail();

    /**
     * Permet de récupérer un utilisateur par son mail
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Permet de récupérer un utilisateur par son email, en excluant l'utilisateur d'ID indiqué
     */
    Optional<User> findOneByIdNotAndEmail(long idUser, String email);

    /**
     * Récupère un utilisateur par son login
     */
    Optional<User> findOneByLogin(String login);

    /**
     * Permet de récupérer un utilisateur par son login, en excluant l'utilisateur d'ID indiqué
     */
    Optional<User> findOneByIdNotAndLogin(long idUser, String login);

    /**
     * Récupère la liste complète des utilisateurs
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Récupère la liste de tous les utilisateurs actifs.
     * @param sort    critères de tri
     * @return la liste de tous les utilisateurs actifs
     */
    List<User> findAllByDisabledFalse(Sort sort);

}
