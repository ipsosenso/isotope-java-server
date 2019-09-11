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

package isotope.modules.security.filters;

import isotope.modules.security.service.JwtProperties;
import isotope.modules.security.service.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * Ce filtre s'occupe de vérifier que le token envoyé par l'utilisateur est valide
 *
 * Created by bbauduin on 02/06/2016.
 */
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtProperties jwtProperties;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			Optional<String> token = Optional.ofNullable(httpRequest.getHeader(jwtProperties.getHeader()));
			setAuthentication(token, jwtTokenUtil, userDetailsService, httpRequest);
		}
		chain.doFilter(request, response);
	}

	/**
	 * On externalise la méthode en static pour pouvoir l'utiliser également dans le ticket filter
	 *
	 * @param token
	 * @param jwtTokenUtil
	 * @param userDetailsService
	 * @param httpRequest
	 */
	public static void setAuthentication(
			Optional<String> token,
			JwtTokenUtil jwtTokenUtil,
			UserDetailsService userDetailsService,
			HttpServletRequest httpRequest
	) {
		token.map(jwtTokenUtil::getUsernameFromToken)
				.ifPresent(
						username -> {
							UserDetails userDetails = userDetailsService.loadUserByUsername(username);
							if (userDetails != null) {
								LOGGER.info(String.format("User `%s` successfully authenticated from JWT token", username));

								UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
								authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
								SecurityContextHolder.getContext().setAuthentication(authentication);
							}
						}
				);
	}
}
