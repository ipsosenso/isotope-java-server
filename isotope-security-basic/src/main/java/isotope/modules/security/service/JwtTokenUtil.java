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

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire qui permet de récupérer et de traiter un token JWT
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenUtil implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

	private static final long serialVersionUID = -3301605591108950415L;

	private static final String CLAIM_KEY_USERNAME = "sub";

	@Autowired
	private JwtProperties properties;

	/**
	 * Retourne l'identifiant de l'utilisateur utilisé pour générer le token ou {@code null} si
	 * une erreur est survenue lors du parsing ou si l'identifiant n'est pas trouvé.
	 *
	 * @param token le token
	 * @return l'identifiant de l'utilisateur utilisé pour générer le token
	 */
	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			final Claims claims = getClaimsFromToken(token);
			if (claims != null) {
				username = claims.getSubject();
			}
		} catch (Exception e) {
			LOGGER.debug("Impossible de recuperer le username depuis ce token", e);
		}
		return username;
	}

	/**
	 * Permet de récupérer une info dans le token JWT
	 *
	 * @param token le token actuel
	 * @param key   la clé de l'objet à ajouter
	 * @return l'objet trouvé dans le token
	 */
	public Object getInfo(String token, String key) {
		final Jws<Claims> jwsClaims = getWholeClaimsFromToken(token);
		final Claims claims = jwsClaims.getBody();
		return claims.get(key);
	}

	/**
	 * Parse le token et retourne son body ou {@code null} si une erreur est survenue lors du parsing.
	 *
	 * @param token le token
	 * @return le body du token
	 */
	private Claims getClaimsFromToken(String token) {
		try {
			return getWholeClaimsFromToken(token).getBody();
		} catch (Exception e) {
			LOGGER.debug("", e);
		}
		return null;
	}

	/**
	 * Parse le token.
	 *
	 * @param token le token à parser
	 * @return le contenu du token
	 */
	private Jws<Claims> getWholeClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(properties.getSecret())
				.parseClaimsJws(token);
	}

	/**
	 * Génère une date d'expiration pour un nouveau token ou {@code null} si le token n'expire pas.
	 *
	 * @param longTerm flag indiquant s'il s'agit d'un token inexpirable
	 * @return la date d'expiration pour un nouveau token
	 */
	protected Date generateExpirationDate(boolean longTerm) {
		if (longTerm) {
			return null;
		}
		return generateExpirationDate(properties.getExpiration() * 1000);
	}

	/**
	 * Génère une date d'expiration pour un nouveau token de validité expiration
	 *
	 * @param expiration combien de temps le token doit être valable en millisecondes
	 * @return la date d'expiration pour un nouveau token
	 */
	protected Date generateExpirationDate(Long expiration) {
		return new Date(System.currentTimeMillis() + expiration);
	}

	/**
	 * Génère un token JWT.
	 *
	 * @param userDetails les infos de l'utilisateur
	 * @param longTerm    flag indiquant s'il s'agit d'un token inexpirable
	 * @return le token généré pour l'utilisateur
	 */
	public String generateToken(UserDetails userDetails, boolean longTerm) {
		return getBasicBuilder(userDetails)
				.setExpiration(generateExpirationDate(longTerm))
				.compact();
	}

	/**
	 * Crée un builder basique pour générer un token JWT sans paramètre
	 *
	 * @param user
	 * @return
	 */
	private JwtBuilder getBasicBuilder(UserDetails user) {
		return getBasicBuilder(user, null);
	}

	/**
	 * Crée un builder basique pour générer un token JWT
	 *
	 * @param user
	 * @param params une association de clé/objet à placer dans le token
	 * @return
	 */
	public JwtBuilder getBasicBuilder(UserDetails user, Map<String, Object> params) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, user.getUsername());
		if (params != null) {
			claims.putAll(params);
		}
		return getBasicBuilder(claims);
	}

	/**
	 * Crée un builder basique pour générer un token JWT
	 *
	 * @param claims
	 * @return
	 */
	public JwtBuilder getBasicBuilder(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, properties.getSecret());
	}

	/**
	 * Génération d'un token standard Isotope
	 *
	 * @param claims
	 * @param longTerm
	 * @return
	 */
	private String generateToken(Map<String, Object> claims, boolean longTerm) {
		JwtBuilder jwtBuilder = getBasicBuilder(claims);
		return jwtBuilder
				.setExpiration(generateExpirationDate(longTerm))
				.compact();
	}

	/**
	 * Retourne une version rafraîchie du token passé en paramètre ou {@code null} si ledit token
	 * est invalide ou expiré.
	 *
	 * Pour un token normal, seule la date de création devrait changer {@link Claims#getIssuedAt()}.
	 * Pour un token inexpirable, on peut faire comme un token normal ou retourner le token tel quel.
	 *
	 * @param token le token à rafraîchir
	 * @return le token rafraîchi ou {@code null} si erreur
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Jws<Claims> jwsClaims = getWholeClaimsFromToken(token);
			final Claims claims = jwsClaims.getBody();
			refreshedToken = generateToken(claims, claims.getExpiration() == null);
		} catch (Exception e) {
			LOGGER.debug("", e);
			refreshedToken = null;
		}
		return refreshedToken;
	}
}
