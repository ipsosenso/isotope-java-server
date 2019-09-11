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

import isotope.modules.user.model.Group;
import isotope.modules.user.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation par défaut du service GroupService
 *
 * Created by qletel on 06/07/2016.
 */
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Optional<Group> getGroupById(int id) {
		return groupRepository.findOptOne(id);
	}

	@Override
	public Optional<Group> getGroupByShortcut(String shortcut) {
		return groupRepository.findByShortcut(shortcut);
	}

	@Override
	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	@Override
	public List<Group> getGroups(Sort sort) {
		return groupRepository.findAll(sort);
	}

	@Override
	public Page<Group> getGroups(Pageable page) {
		return groupRepository.findAll(page);
	}
}
