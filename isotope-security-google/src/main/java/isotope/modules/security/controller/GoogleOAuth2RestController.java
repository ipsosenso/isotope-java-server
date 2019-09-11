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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import isotope.modules.security.GoogleJwtUser;
import isotope.modules.security.JwtUser;
import isotope.modules.security.exceptions.UnknownAccountException;
import isotope.modules.security.service.CheckGoogleOauth2Service;
import isotope.modules.security.service.GoogleServiceImpl;
import isotope.modules.security.service.JwtProperties;
import isotope.modules.user.IJwtUser;
import isotope.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Ce controller s'occupe d'appeler le service de vérification du Google ID et si c'est ok, il utilise le service
 * de génération de token pour le renvoyer au front
 * <p>
 * Created by bbauduin on 02/11/2016.
 */
@RestController
@RequestMapping(value = "${jwt.route.authentication.path}")
@Primary
public class GoogleOAuth2RestController implements IAuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleOAuth2RestController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private GoogleServiceImpl googleService;

    @Autowired
    private CheckGoogleOauth2Service checkGoogleOauth2Service;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserService userService;

    /**
     * Récupère un googleId et un username et renvoie un token JWT isotope
     *
     * @param authenticationRequest Requête provenant de l'appli front
     * @throws AuthenticationException si l'utilisateur ne peut pas être authentifié
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody GoogleAuthenticationRequest authenticationRequest) throws AuthenticationException {

        try {
            //Premièrement, on vérifie que le user connecté est valide auprès de Google
            checkGoogleOauth2Service.checkUsername(authenticationRequest.getUsername());
            GoogleIdToken compte = checkGoogleOauth2Service.check(authenticationRequest.getGoogleId());

            GoogleJwtUser userDetails;

            //On récupère les informations depuis la BDD
            try {
                JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(compte.getPayload().getEmail());
                userDetails = new GoogleJwtUser(user, authenticationRequest.getGoogleId(), user.getAuthorities());
            } catch (UsernameNotFoundException e) {
                //Si je n'ai pas trouvé le user en base, je le crée car il a été authentifié
                userDetails = (GoogleJwtUser) googleService.createUser(compte);
            }

            //Je place mon Google id dans le user details
            userDetails.setGoogleId(authenticationRequest.getGoogleId());

            //Je place l'authentication dans le contexte spring
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return the token
            return ok(new JwtAuthenticationResponse(authenticationRequest.getGoogleId()));

        } catch (UnknownAccountException e) {
            LOGGER.debug("Erreur lors de la connexion", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ResponseEntity getAuthenticatedUser(@AuthenticationPrincipal IJwtUser user) {
        if (user != null) {
            String username = user.getUsername();
            //Construction du user en fonction du token
            return ok(userDetailsService.loadUserByUsername(username));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {

        String token = request.getHeader(jwtProperties.getHeader());
        try {
            GoogleIdToken compte = checkGoogleOauth2Service.check(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (UnknownAccountException e) {
            LOGGER.error("Impossible d'authentifier le compte avec le token : " + token, e);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
