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

import isotope.modules.security.service.JwtTokenUtil;
import isotope.modules.security.service.TicketService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Vérifie qu'une url donnée possède bien un paramètre ticket et que celui-ci est valide
 *
 * Created by bbauduin on 16/03/2017.
 */
@PropertySource("classpath:/application-isotope-security.properties")
public class TicketFilter implements Filter {

	private UserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;

	private String ticketKeyParameter;
	private TicketService ticketService;

	public TicketFilter(
			String ticketKeyParameter,
			TicketService ticketService,
			UserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil
	) {
		this.ticketKeyParameter = ticketKeyParameter;
		this.ticketService = ticketService;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI();
		String ticket = httpRequest.getParameter(ticketKeyParameter);

		if (ticketService.checkTicket(path, ticket)) {
			// Si c'est ok, je vais aller chercgher mon utilisateur
			Optional<String> token = Optional.ofNullable(ticket);
			JwtAuthenticationTokenFilter.setAuthentication(token, jwtTokenUtil, userDetailsService, httpRequest);
			filterChain.doFilter(request, servletResponse);
		} else {
			HttpServletResponse res = (HttpServletResponse) servletResponse;
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	public void destroy() {

	}
}
