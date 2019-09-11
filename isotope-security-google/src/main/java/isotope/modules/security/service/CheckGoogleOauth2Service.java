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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import isotope.modules.security.exceptions.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Permet de vérifier qu'un token de connexion est valide auprès du service OAuth2 de Google
 *
 * Created by bbauduin on 02/11/2016.
 */
@Service
@EnableConfigurationProperties(GoogleOAuth2Properties.class)
public class CheckGoogleOauth2Service {


    private static final Logger LOGGER = LoggerFactory.getLogger(CheckGoogleOauth2Service.class);


    private GoogleOAuth2Properties googleOAuth2Properties;


    /**
     * Constructeur utilisé pour injecter la configuration Oauth
     * @param googleOAuth2Properties
     */
    public CheckGoogleOauth2Service(GoogleOAuth2Properties googleOAuth2Properties){
        this.googleOAuth2Properties = googleOAuth2Properties;
    }


    /**
     * Vérifie qu'un username correspond bien au compte configuré
     *
     * @param username le username que l'on veut identifier
     * @return
     * @throws UnknownAccountException
     */
    public boolean checkUsername(String username) throws UnknownAccountException {

        if (!username.contains("@") || !googleOAuth2Properties.getDomainRestrictions().contains(username.substring(username.indexOf("@")+1))) {
            throw new UnknownAccountException("Le username " + username + " ne correspond pas au domaine configure "
                    + googleOAuth2Properties.getDomainRestrictions());
        }

        return true;
    }

    /**
     * Vérifie un couple username / id auprès d'un serveur OAuth2.
     * Le user doit correspondre au domaine configuré dans l'application (via la liste des domaines authorisés)
     *
     * @param googleId le google id du user à identifier
     * @return le GoogleIdToken qui contient les infos de l'utilisateur
     */
    public GoogleIdToken check(String googleId) throws UnknownAccountException {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("trying google authentification on " + googleOAuth2Properties.getDomainRestrictions());
        }

        try {
            NetHttpTransport transport = new NetHttpTransport();
            GsonFactory jsonFactory = new GsonFactory();
            //Check du token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList(googleOAuth2Properties.getId()))
                    .setIssuer("accounts.google.com")
                    .build();

            GoogleIdToken idToken = verifier.verify(googleId);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                if (isDomainAuthorized(payload.getEmailVerified(), payload.getHostedDomain())) {
                    return idToken;
                }
            }
        } catch (Throwable t) {
            LOGGER.error("error while google authentificating on " + googleOAuth2Properties.getDomainRestrictions(), t);
        }
        throw new UnknownAccountException("Le token Google n'a pas pu etre verifie");
    }

    /**
     * Vérifier que le domaine utilisé par l'utilisateur est bien whitelisté par l'application
     *
     * @param isEmailVerified
     * @param payloadHostedDomain
     * @return
     */
    public boolean isDomainAuthorized(boolean isEmailVerified, String payloadHostedDomain){
        return isEmailVerified
                && payloadHostedDomain != null
                && googleOAuth2Properties.getDomainRestrictions().contains(payloadHostedDomain);
    }
}
