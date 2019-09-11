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

import isotope.commons.exceptions.NotFoundException;
import isotope.commons.services.CrudEntityService;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.lightbeans.FunctionLightBean;
import isotope.modules.user.model.Function;
import isotope.modules.user.repository.FunctionRepository;
import isotope.modules.user.repository.specifications.FunctionSpecification;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class FunctionService extends CrudEntityService<FunctionRepository, Function, Long> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionService.class);

	@Autowired
	UrlService urlService;

	@Autowired
	AssoFunctionRoleService functionRoleService;

	public FunctionService (FunctionRepository repository){
		super(repository);
	}

	/**
	 * @return la liste de toutes les fonctions en base
	 */
	public List<FunctionLightBean> getFunctions() {
		return StreamSupport.stream(findAll().spliterator(), false)
				.map(LightBeanToBeanUtils::copyFrom)
				.collect(Collectors.toList());
	}

	/**
	 * Attention, cette méthode retourne toutes les fonctions
	 * qui sont bien définies en base. Pour récupérer les fonctions qui n'ont aucun droit et donc accessibles quand
	 * même, il faut utiliser {@link #getFunctionsWithoutAssoRole()}
	 *
	 * @param user
	 * @return toutes les fonctions auxquelles le user à le droit.
	 */
	public Stream<FunctionLightBean> getFunctions(IJwtUser user) {
		return getFunctions(user.getId());
	}

	/**
	 * @param idUser
	 * @return la liste des fonctions auxquelles à le droit un utilisateur à partir de ces rôles
	 */
	public Stream<FunctionLightBean> getFunctions(long idUser) {
		return repository.findAll(FunctionSpecification.findByUser(idUser)).stream().map(LightBeanToBeanUtils::copyFrom);
	}

	/**
	 * @return toutes les fonctions qui ne sont pas associées à un rôle
	 */
	public Stream<FunctionLightBean> getFunctionsWithoutAssoRole() {
		List<Long> functions = functionRoleService.findDistinctIdFunction();
		if (functions.isEmpty()) {
			return repository.findAll()
					.stream()
					.map(LightBeanToBeanUtils::copyFrom);
		} else {
			return repository.findAll(FunctionSpecification.findNotInList(functions))
					.stream()
					.map(LightBeanToBeanUtils::copyFrom);
		}
	}

	/**
	 * Permet de savoir si une fonction est accessible à un utilisateur
	 *
	 * @param user
	 * @param path
	 * @return
	 */
	public boolean isAuthorized(IJwtUser user, String path) {
		/**
		 * Si je reçois : /outils/empreinte/listeProjets
		 * Je vais vérifier les droits dans l'ordre suivant :
		 * 		/outils
		 * 		/outils/empreinte
		 *		/outils/empreinteListeProjets
		 */

		// Premièrement, on ne vérifie pas un path de 1 caractère "/"
		if (path.length() > 1) {
			String[] splittedPath = path.split("/");
			StringBuilder sb = new StringBuilder();
			boolean functionTrouvee = false;
			for (String pathPart : splittedPath) {
				if (!pathPart.isEmpty()) {
					String url = sb.append("/" + pathPart).toString();
					try {
						Long idFunction = urlService.findByUrl(url).getIdFunction();
						functionTrouvee = true;
						// Si la fonction n'est associée à rien alors j'ai les droits !
						if (functionRoleService.countByIdFunction(idFunction) == 0) {
							return true;
						}
						// Sinon je regarde si j'ai les droits pour mon user
						Function function = repository.findOne(
								Specifications
										.where(FunctionSpecification.findById(idFunction))
										.and(FunctionSpecification.findByUser(user.getId()))
						);
						if (function != null) {
							return true;
						}
					} catch (NotFoundException e) {
						LOGGER.debug("Impossible de trouver l'url donnee : " + url, e);
					}
				}
			}
			// Si j'ai trouvé au moins une fonction et que je n'ai pas retourné "true", c'est que je n'avais pas les droits
			return !functionTrouvee;
		}
		return true;
	}

}
