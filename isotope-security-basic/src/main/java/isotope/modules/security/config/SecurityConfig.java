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


import isotope.modules.security.JwtAuthenticationEntryPoint;
import isotope.modules.security.service.JwtTokenUtil;
import isotope.modules.security.filters.JwtAuthenticationTokenFilter;
import isotope.modules.security.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

/**
 * Cette classe permet de configurer toute la partie authentification de l'application
 *
 * Created by qletel on 20/05/2016.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(90)
@PropertySource("classpath:/application-isotope-security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected JwtAuthenticationEntryPoint unauthorizedHandler;

	@Value("${jwt.route.authentication.path}")
	private String loginPath;

	@Value("${security.paths.whitelist:}")
	private String whiteList;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * Redéfinir cette fonction nous permet de changer le userDetailService du manager par défaut ainsi que
	 * l'encodeur de mot de passe !
	 */
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(this.userDetailsService)
				.passwordEncoder(this.passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Permet d'ajouter le filtre JEE de Spring qui nous permet l'authentification par token JWT
	 */
	@Bean
	public UsernamePasswordAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	/**
	 * Ajout de la configuration permettant de récupérer une instance du ticket service
	 *
	 * @return un {@link TicketService}
	 */
	@Bean
	public TicketService getTicketService() {
		return new TicketService(jwtTokenUtil);
	}

	/**
	 * Configuration de spring security
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlAuthorizationConfigurer =
				httpSecurity
						// we don't need CSRF because our token is invulnerable
						.csrf().disable()
						.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
						.and()

						// don't create session
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

						.authorizeRequests()
						.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.antMatchers(loginPath + "/**").permitAll()
						.antMatchers("/api/public/**").permitAll()
						.antMatchers("/img/**").permitAll();

		// Ajout des paths whitelistés configurés depuis le application-properties
		if (!whiteList.isEmpty()) {
			for (String path : whiteList.split(",")) {
				urlAuthorizationConfigurer.antMatchers(path).permitAll();
			}
		}

		urlAuthorizationConfigurer.anyRequest().authenticated();

		Map<String, IsotopeSecurityConfigurer> beans = applicationContext.getBeansOfType(IsotopeSecurityConfigurer.class);
		for (IsotopeSecurityConfigurer isotopeSecurityConfigurer : beans.values()) {
			httpSecurity.apply(isotopeSecurityConfigurer);
		}

		// Custom JWT based security filter
		httpSecurity
				.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		httpSecurity.headers().cacheControl();
	}

}
