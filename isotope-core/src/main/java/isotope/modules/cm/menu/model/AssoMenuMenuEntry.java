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

package isotope.modules.cm.menu.model;

import isotope.commons.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "is_asso_menu_menu_entry"
)
public class AssoMenuMenuEntry extends BaseEntity {
  @Column(
      name = "id_menu",
      nullable = false
  )
  private Long idMenu;

  @Column(
      name = "id_menu_entry",
      nullable = false
  )
  private Long idMenuEntry;

  @Column(
      name = "ordre",
      nullable = false
  )
  private Integer ordre;

  public AssoMenuMenuEntry() {
  }

  public Long getIdMenu() {
    return this.idMenu;
  }

  public void setIdMenu(Long idMenu) {
    this.idMenu = idMenu;
  }

  public Long getIdMenuEntry() {
    return this.idMenuEntry;
  }

  public void setIdMenuEntry(Long idMenuEntry) {
    this.idMenuEntry = idMenuEntry;
  }

  public Integer getOrdre() {
    return this.ordre;
  }

  public void setOrdre(Integer ordre) {
    this.ordre = ordre;
  }
}
