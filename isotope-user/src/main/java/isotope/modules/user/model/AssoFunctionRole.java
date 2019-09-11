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

package isotope.modules.user.model;

import isotope.commons.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "is_asso_function_role"
)
public class AssoFunctionRole extends BaseEntity {

  @Column(
      name = "id_function",
      nullable = false
  )
  private Long idFunction;

  @Column(
      name = "id_role",
      nullable = false
  )
  private Long idRole;

  public AssoFunctionRole() {
  }


  public Long getIdFunction() {
    return this.idFunction;
  }

  public void setIdFunction(Long idFunction) {
    this.idFunction = idFunction;
  }

  public Long getIdRole() {
    return this.idRole;
  }

  public void setIdRole(Long idRole) {
    this.idRole = idRole;
  }
}
