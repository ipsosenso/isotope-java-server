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

package isotope.commons.events;

import com.google.common.base.Preconditions;
import isotope.commons.entities.BaseEntity;

/**
 * Created by oturpin on 16/06/16.
 */
public class BeforeSaveEntityEvent <T extends BaseEntity> {

    private final T entity;

    public BeforeSaveEntityEvent(final T entity) {
        this.entity = entity;
    }

    public final T getEntity() {
        return Preconditions.checkNotNull(entity);
    }


}
