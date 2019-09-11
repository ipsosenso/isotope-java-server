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

package isotope.modules.security.config;

import isotope.modules.security.filters.TicketFilter;
import isotope.modules.security.service.JwtTokenUtil;
import isotope.modules.security.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration spécifique à la partie filtre avec ticket
 *
 * Created by bbauduin on 24/04/2017.
 */
@Configuration
@Order(80)
@ConditionalOnProperty("security.ticket.urlliste")
@PropertySource("classpath:/application-isotope-security.properties")
public class TicketSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${security.ticket.urlliste:}")
	private String urlsTicketListe;

	@Value("${security.ticket.parameterkey:ticket}")
	private String ticketKeyParameter;

	/**
	 * Configuration de spring security
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// Custom ticket filter
		if (!urlsTicketListe.isEmpty()) {
			String[] urls = urlsTicketListe.split(",");
			httpSecurity
					.requestMatchers()
					.antMatchers(urls).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests()
					.antMatchers(urls).permitAll().and()
					.addFilterBefore(new TicketFilter(ticketKeyParameter, ticketService, userDetailsService, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
		}
	}

}
