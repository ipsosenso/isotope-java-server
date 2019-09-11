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

package isotope.modules.cm.menu.service;

import isotope.commons.services.CrudEntityService;
import isotope.modules.cm.menu.constant.MenuEntryType;
import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.model.MenuEntry;
import isotope.modules.cm.menu.repository.MenuEntryRepository;
import isotope.modules.cm.menu.repository.specifications.MenuEntrySpecification;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.stream.Stream;

@Service
public class MenuEntryService extends CrudEntityService<MenuEntryRepository, MenuEntry, Long> {

	/**
	 * Base constructor
	 *
	 * @param repository
	 */
	public MenuEntryService(MenuEntryRepository repository) {
		super(repository);
	}

	/***
	 * Récupère toutes les entrées de menu associées à un menu
	 * @param idMenu
	 * @return
	 */
	public Stream<MenuProfilEntry> getMenuEntry(Long idMenu) {
		return repository.findByMenu(idMenu).map(o -> new MenuProfilEntry(
				((BigInteger) o[MenuEntrySpecification.INDEX_ID]).longValue(),
				(String) o[MenuEntrySpecification.INDEX_CODE],
				o[MenuEntrySpecification.INDEX_ID_function] != null ? ((BigInteger) o[MenuEntrySpecification.INDEX_ID_function]).longValue() : null,
				o[MenuEntrySpecification.INDEX_ID_PARENT] != null ? ((BigInteger) o[MenuEntrySpecification.INDEX_ID_PARENT]).longValue() : null,
				MenuEntryType.valueOf((String) o[MenuEntrySpecification.INDEX_TYPE]),
				(Integer) o[MenuEntrySpecification.INDEX_ORDRE],
				(String) o[MenuEntrySpecification.INDEX_ICON],
				(String) o[MenuEntrySpecification.INDEX_URL],
				(String) o[MenuEntrySpecification.INDEX_URL_function])
		);
	}
}
