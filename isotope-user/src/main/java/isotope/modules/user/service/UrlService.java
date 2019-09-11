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
import isotope.modules.user.lightbeans.UrlLightBean;
import isotope.modules.user.model.Url;
import isotope.modules.user.repository.UrlRepository;
import isotope.modules.user.utils.LightBeanToBeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlService extends CrudEntityService<UrlRepository, Url, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public UrlService(UrlRepository repository) {
		super(repository);
	}

	/**
	 * Retourne un url light bean pour une url donnée (attention, on ne gère pas la langue ici)
	 * @param url
	 * @return
	 */
	public UrlLightBean findByUrl(String url) {
		return LightBeanToBeanUtils.copyFrom(repository.findByUrl(url).orElseThrow(NotFoundException::new));
	}

}
