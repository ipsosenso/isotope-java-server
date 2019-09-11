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
import java.util.Optional;

@Entity
@Table(
    name = "`group`"
)
public class Group extends BaseEntity {
  @Column(
      name = "name",
      length = 45
  )
  private String name;

  @Column(
      name = "shortcut",
      nullable = false,
      length = 45
  )
  private String shortcut;

  public Group() {
  }

  public Optional<String> getName() {
    return Optional.ofNullable(this.name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortcut() {
    return this.shortcut;
  }

  public void setShortcut(String shortcut) {
    this.shortcut = shortcut;
  }
}
