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


import isotope.modules.user.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	@Override
	@Deprecated
	Group findOne(Integer integer);

	/**
	 * Alias pour la méthode {@link #findOne(Integer)} mais qui retourne un
	 * Optional à la place.
	 *
	 * @param id    l'id de l'objet
	 * @return l'objet trouvé
	 */
	@SuppressWarnings("deprecated")
	default Optional<Group> findOptOne(Integer id){
		return Optional.ofNullable(getOne(id));
	}

	Optional<Group> findByShortcut(String shortcut);
}
