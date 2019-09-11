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

import isotope.commons.persistence.IdGenerator;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 * Classe mère de tous les beans persistés en base via JPA
 * Centralise la gestion des identifiants
 *
 * Created by oturpin on 16/06/16.
 */
@MappedSuperclass
public class BaseEntity {

    @Id
    private Long id;

    /**
     * La génération des identifiants est réalisée avant de persister
     * le bean en base grâce au callBack @PrePersist
     *
     */
    @PrePersist
    public void generateId() {
        if (id == null) {
            id = IdGenerator.generate();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
