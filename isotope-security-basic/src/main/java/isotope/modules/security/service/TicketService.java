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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import isotope.modules.security.beans.Ticket;
import isotope.modules.user.IJwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Permet de créer et vérifier un ticket pour Isotope.
 *
 * Created by bbauduin on 17/03/2017.
 */
public class TicketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

	@Value("${security.ticket.validity:30000}")
	private long validity;

	@Value("${security.ticket.jwt.key:path}")
	private String jwtKeyPath;

	/**
	 * Utilisé pour générer le ticket
	 */
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * Constructeur par défaut qui prend en paramètre les services à utiliser
	 */
	public TicketService(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}


	/**
	 * Vérifie si un ticket est valable. On regarde si le token JWT est valide et si le path contenu dedans correspond
	 * bien au path qu'on demande.
	 *
	 * @param path
	 * @param ticket
	 * @return vrai si le token est valide
	 */
	public boolean checkTicket(String path, String ticket) {
		if (ticket == null || path == null) return false;
		try {
			String pathJWT = (String) jwtTokenUtil.getInfo(ticket, jwtKeyPath);
			return pathJWT.equals(path);
		} catch (ExpiredJwtException e) {
			LOGGER.debug("Token expire, impossible de telecharger le fichier", e);
		}
		return false;
	}

	/**
	 * Permet de récupérer une chaine signée contenant le path et un timestamp valable "validity" ms
	 *
	 * @param user représente le user connecté en cours
	 * @param path représente le path à placer dans le ticket
	 * @return un ticket JWT contenant le path
	 */
	public Ticket generateTicket(IJwtUser user, String path) {
		// Si j'ai une queryString, je la supprime
		int indexQueryString = path.indexOf('?');
		if (indexQueryString > 0) {
			path = path.substring(0, indexQueryString);
		}
		Map<String, Object> params = new HashMap<>();
		params.put(jwtKeyPath, path);
		JwtBuilder jwtBuilder = jwtTokenUtil.getBasicBuilder(user, params);
		jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + validity));
		return new Ticket(jwtBuilder.compact());
	}

}
