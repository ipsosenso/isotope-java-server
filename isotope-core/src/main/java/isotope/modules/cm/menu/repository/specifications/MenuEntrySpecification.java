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

package isotope.modules.cm.menu.repository.specifications;

import isotope.modules.cm.menu.constant.MenuEntryType;
import isotope.modules.cm.menu.lightbeans.MenuProfilEntry;
import isotope.modules.cm.menu.model.AssoMenuMenuEntry;
import isotope.modules.cm.menu.model.AssoMenuMenuEntry_;
import isotope.modules.cm.menu.model.MenuEntry;
import isotope.modules.cm.menu.model.MenuEntry_;
import isotope.modules.user.model.Url;
import isotope.modules.user.model.Url_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Regroupe toutes les spécifications relatives aux entrées de menu
 *
 * Created by bbauduin on 23/12/2016.
 */
public class MenuEntrySpecification {

	public static final int INDEX_ID = 0;
	public static final int INDEX_CODE = 1;
	public static final int INDEX_ID_function = 2;
	public static final int INDEX_ID_PARENT = 3;
	public static final int INDEX_TYPE = 4;
	public static final int INDEX_ICON = 5;
	public static final int INDEX_ORDRE = 6;
	public static final int INDEX_URL = 7;
	public static final int INDEX_URL_function = 8;

	public static MenuProfilEntry convertTupleMenuProfilEntry(Tuple tuple) {
		return new MenuProfilEntry(
				tuple.get(INDEX_ID, Long.class),
				tuple.get(INDEX_CODE, String.class),
				tuple.get(INDEX_ID_function, Long.class),
				tuple.get(INDEX_ID_PARENT, Long.class),
				tuple.get(INDEX_TYPE, MenuEntryType.class),
				tuple.get(INDEX_ORDRE, Integer.class),
				tuple.get(INDEX_ICON, String.class),
				tuple.get(INDEX_URL, String.class),
				tuple.get(INDEX_URL_function, String.class)
		);
	}

	/**
	 * Retourne les entrée de menu pour un menu donné
	 *
	 * @param idMenu
	 * @return
	 */
	public static Specification<MenuEntry> findByMenu(long idMenu) {
		return (Root<MenuEntry> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// On relie les entrées de menu et le menu avec l'asso
			Root<AssoMenuMenuEntry> assoMenuMenuEntry = criteriaQuery.from(AssoMenuMenuEntry.class);
			predicates.add(criteriaBuilder.equal(assoMenuMenuEntry.get(AssoMenuMenuEntry_.idMenu), idMenu));
			predicates.add(criteriaBuilder.equal(assoMenuMenuEntry.get(AssoMenuMenuEntry_.idMenuEntry), root.get(MenuEntry_.id)));

			// On relie aux URLs
			Root<Url> urlLink = criteriaQuery.from(Url.class);
			predicates.add(criteriaBuilder.or(
					criteriaBuilder.equal(urlLink.get(Url_.idFunction), root.get(MenuEntry_.idFunction)),
					criteriaBuilder.isNull(root.get(MenuEntry_.idFunction))
			));

			//Précision de la sélection
			((CriteriaQuery<Tuple>) criteriaQuery).select(criteriaBuilder.tuple(
					root.get(MenuEntry_.id),
					root.get(MenuEntry_.code),
					root.get(MenuEntry_.idFunction),
					root.get(MenuEntry_.idParent),
					root.get(MenuEntry_.type),
					root.get(MenuEntry_.icon),
					assoMenuMenuEntry.get(AssoMenuMenuEntry_.ordre),
					root.get(MenuEntry_.url),
					urlLink.get(Url_.url)
			));

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

}
