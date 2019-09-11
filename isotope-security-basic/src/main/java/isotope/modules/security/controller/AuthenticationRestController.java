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

package isotope.modules.security.controller;


import isotope.modules.security.service.JwtTokenUtil;
import isotope.modules.security.service.JwtProperties;
import isotope.modules.user.IJwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Contient la méthode de login et de récupération d'un utilisateur loggué
 */
@RestController
@ConditionalOnMissingBean(IAuthRestController.class)
@RequestMapping(value = "${jwt.route.authentication.path}")
public class AuthenticationRestController {

	@Autowired
	private JwtProperties jwtProperties;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

		// Perform the security
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails, authenticationRequest.isRememberMe());

		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public IJwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(jwtProperties.getHeader());
		String username = jwtTokenUtil.getUsernameFromToken(token);
		return (IJwtUser) userDetailsService.loadUserByUsername(username);
	}

	@RequestMapping(value = "refresh", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(jwtProperties.getHeader());
		String username = jwtTokenUtil.getUsernameFromToken(token);
		IJwtUser user = (IJwtUser) userDetailsService.loadUserByUsername(username);

		String refreshedToken = jwtTokenUtil.refreshToken(token);
		if (refreshedToken == null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
	}

}
