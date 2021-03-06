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

package isotope.commons.exceptions;

/**
 * Created by oturpin on 16/06/16.
 */
public class IsotopeEntityNotFoundException extends RuntimeException {

    public IsotopeEntityNotFoundException() {
        super();
    }

    public IsotopeEntityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public IsotopeEntityNotFoundException(final String message) {
        super(message);
    }

    public IsotopeEntityNotFoundException(final Throwable cause) {
        super(cause);
    }
}
