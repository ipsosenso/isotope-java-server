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

package isotope.modules.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Configuration pour Swagger déclenchée par la présence de l'annotation {@link EnableSwagger2}.
 *
 * Created by qletel on 16/02/2017.
 */
@Configuration
@ConditionalOnBean(annotation = EnableSwagger2.class)
public class IsotopeSwaggerConfiguration {


	/**
	 * Configure les dockets projet et isotope par défaut.
	 */
	@ConditionalOnProperty(value = "isotope.swagger.defaultDocket.enabled", matchIfMissing = true)
	class IsotopeSwaggerDocketsConfiguration {

		private static final String ISOTOPE_API_DOCKET_NAME = "isotope";
		private static final String DEFAULT_API_DOCKET_NAME = "api";

		/**
		 * @return le docket pour l'api isotope
		 */
		@Bean
		public Docket isotopeApiDocket() {
			return applyStandardConfiguration(new Docket(DocumentationType.SWAGGER_2)
					.groupName(ISOTOPE_API_DOCKET_NAME)
					.select()
					.apis(isotopeApiPredicate())
					.build()
					.apiInfo(new ApiInfoBuilder().build()));
		}

		/**
		 * @return le docket pour l'api hors isotope
		 */
		@Bean
		public Docket defaultApiDocket() {
			return applyStandardConfiguration(new Docket(DocumentationType.SWAGGER_2)
					.groupName(DEFAULT_API_DOCKET_NAME)
					.select()
					.apis(Predicates.not(isotopeApiPredicate()))
					.build()
					.apiInfo(new ApiInfoBuilder().build()));
		}

		/**
		 * Filtre pour capturer les services d'isotope.
		 *
		 * @return le filtre pour l'api isotope
		 */
		private Predicate<RequestHandler> isotopeApiPredicate() {
			return RequestHandlerSelectors.basePackage("isotope");
		}

		/**
		 * Configure un docket pour utiliser un certain nombre de standards.
		 *
		 * @param docket le docket à configurer
		 * @return le docket configuré
		 */
		private Docket applyStandardConfiguration(Docket docket) {
			return docket
					.genericModelSubstitutes(ResponseEntity.class)
					.directModelSubstitute(LocalDate.class, Date.class)
					.directModelSubstitute(LocalDateTime.class, java.util.Date.class);
		}

	}

	/**
	 * Ouvre les routes pour Swagger.
	 */
	@Order(70)
	@Configuration
	class IsotopeSwaggerSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Value("${springfox.documentation.swagger.v2.path:/v2/api-docs}")
		private String swaggerApiDocs;

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity
					.requestMatchers()
					.antMatchers(swaggerApiDocs + "/**")
					.antMatchers("/webjars/**")
					.antMatchers("/configuration/**")
					.antMatchers("/swagger-resources/**")
					.antMatchers("/swagger-ui.html")
					.and()
					.authorizeRequests()
					.anyRequest()
					.permitAll()
			;
		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				// surcharge de swagger-ui.html
				registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/public/swagger-ui.html");
			}
		};
	}

}
