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

package isotope.commons.entities;


import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Interface à implémenter pour un suivi basique de la création
 * et de la modification d'un objet.
 *
 * Created by qletel on 14/06/2016.
 */
public interface IHistory {

    Optional<Long> getIdUserCreation();
    Optional<Long> getIdUserModification();
    LocalDateTime getDateCreation();
    Optional<LocalDateTime> getDateModification();

}
