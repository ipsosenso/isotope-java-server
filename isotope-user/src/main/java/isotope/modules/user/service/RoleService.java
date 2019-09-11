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

import isotope.commons.services.CrudEntityService;
import isotope.modules.user.lightbeans.RoleLightBean;
import isotope.modules.user.model.Role;
import isotope.modules.user.repository.RoleRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService extends CrudEntityService<RoleRepository, Role, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public RoleService(RoleRepository repository) {
		super(repository);
	}

	/**
	 * @return un stream de tous les rôles enregistrés
	 */
	public List<RoleLightBean> getRoles() {
		return StreamSupport.stream(findAll().spliterator(), false)
				.map(LightBeanToBeanUtils::copyFrom)
				.collect(Collectors.toList());
	}

}
