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
public class ApiPreconditions {

    public static void checkResourceExist(final boolean resourceExists){
        if( !resourceExists ){
            throw new IsotopeEntityNotFoundException();
        }
    }

    /**
     * Vérifier si une resource existe
     *
     * @param resource
     * @param <T>
     * @return
     */
    public static <T> T checkResourceExist(final T resource) {
        if (resource == null) {
            throw new IsotopeEntityNotFoundException();
        }
        return resource;
    }

}
