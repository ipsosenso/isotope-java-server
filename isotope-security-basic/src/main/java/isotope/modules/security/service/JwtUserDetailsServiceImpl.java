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

package isotope.modules.security.service;


import isotope.modules.security.JwtUser;
import isotope.modules.security.locale.ILocaleService;
import isotope.modules.user.model.User;
import isotope.modules.user.repository.UserRepository;
import isotope.modules.user.service.FunctionService;
import isotope.modules.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service qui permet de récupérer un utilisateur au moment du login
 */
@Service
@ConditionalOnMissingClass("UserDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ILocaleService localeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	/**
	 * Retourne un {@link JwtUser} correspondant au login passé en paramètre.
	 * <p>
	 * <b>Note :</b> Il semblerait que l'annotation Transactional soit nécessaire, sinon on a l'erreur :
	 * <p>
	 * failed to lazily initialize a collection of role: User.group, could not initialize proxy - no Session
	 *
	 * @param username le login de l'utilisateur
	 * @return l'utilisateur correspondant au {@code username} sous la forme d'un objet {@code JwtUser}.
	 * @throws UsernameNotFoundException
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// On récupère le user
		User user = userRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));

		// On va récupérer ces droits de l'utilisateur
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role ->
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().toUpperCase()))
		);
		functionService.getFunctionsWithoutAssoRole().forEach(functionLightBean ->
				authorities.add(new SimpleGrantedAuthority(functionLightBean.getCode()))
		);
		functionService.getFunctions(user.getId()).forEach(functionLightBean ->
				authorities.add(new SimpleGrantedAuthority(functionLightBean.getCode()))
		);
		return new JwtUser(user, localeService.getUserLocale(user), authorities);
	}
}
