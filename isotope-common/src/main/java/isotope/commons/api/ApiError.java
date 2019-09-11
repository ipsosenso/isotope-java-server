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

package isotope.commons.api;

/**
 * Created by oturpin on 16/06/16.
 */
public class ApiError {

	/**
	 * Code erreur "métier"
	 */
	private String error;

	/**
	 * Message explicite
	 */
	private String error_details;

	/* GETTERS & SETTERS */
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_details() {
		return error_details;
	}

	public void setError_details(String error_details) {
		this.error_details = error_details;
	}
}
