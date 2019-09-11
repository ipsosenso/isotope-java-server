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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Contient les informations de connexion à l'API OAuth2 Google
 *
 * Created by bbauduin on 04/11/2016.
 */
@ConfigurationProperties(prefix = "google.client")
public class GoogleOAuth2Properties {


    /** Liste des domaines autorisés à se connecter via Oauth */
    private List<String> domainRestrictions;

    /** Google client id utilisé pour identifier l'origine de la requête vis à vis du service Oauth2 */
    private String id;


    public List<String> getDomainRestrictions() {
        return domainRestrictions;
    }

    public void setDomainRestrictions(List<String> domainRestrictions) {
        this.domainRestrictions = domainRestrictions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
