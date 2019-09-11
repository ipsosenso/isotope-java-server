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
import isotope.modules.user.lightbeans.AssoFunctionRoleLightBean;
import isotope.modules.user.model.AssoFunctionRole;
import isotope.modules.user.repository.AssoFunctionRoleRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AssoFunctionRoleService extends CrudEntityService<AssoFunctionRoleRepository, AssoFunctionRole, Long> {


	/**
	 * Constructeur
	 * @param repository
	 */
	public AssoFunctionRoleService(AssoFunctionRoleRepository repository){
		super(repository);
	}

	/**
	 * @return toutes les associations connues entre les fonctions et les rôles
	 */
	public List<AssoFunctionRoleLightBean> getAllAssos() {
		return StreamSupport.stream(findAll().spliterator(), false)
				.map(LightBeanToBeanUtils::copyFrom)
				.collect(Collectors.toList());
	}

	/**
	 * Sauvegarde les nouvelles associations passées en paramètres en supprimant les anciennes avant
	 * @param assos
	 * @return
	 */
	@Transactional
	public List<AssoFunctionRoleLightBean> changeAssos(List<AssoFunctionRoleLightBean> assos) {
		repository.deleteAll();
		repository.flush();
		assos.forEach(assoFunctionRoleLightBean -> {
			AssoFunctionRole asso = new AssoFunctionRole();
			asso.setIdRole(assoFunctionRoleLightBean.getIdRole());
			asso.setIdFunction(assoFunctionRoleLightBean.getIdFunction());
			repository.save(asso);
		});
		return getAllAssos();
	}

	/**
	 * @return la liste des id de fonctions
	 */
	public List<Long> findDistinctIdFunction() {
		return repository.findDistinctIdFunction();
	}

	/**
	 * @param idFunction
	 * @return le nombre de fois qu'une fonction existe dans la table des associations
	 */
	public Long countByIdFunction(Long idFunction) {
		return repository.countByIdFunction(idFunction);
	}
}
