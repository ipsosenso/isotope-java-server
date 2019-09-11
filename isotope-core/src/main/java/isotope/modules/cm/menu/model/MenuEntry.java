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
import isotope.modules.cm.menu.constant.MenuEntryType;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(
    name = "is_menu_entry"
)
public class MenuEntry extends BaseEntity {
  @Column(
      name = "code",
      length = 50
  )
  private String code;

  @Enumerated(EnumType.STRING)
  @Column(
      name = "type",
      nullable = false,
      length = 20
  )
  private MenuEntryType type;

  @Column(
      name = "icon",
      length = 65535
  )
  private String icon;

  @Column(
      name = "url",
      length = 65535
  )
  private String url;

  @Column(
      name = "id_function",
      nullable = false
  )
  private Long idFunction;

  @Column(
      name = "id_parent"
  )
  private Long idParent;

  public MenuEntry() {
  }

  public Optional<String> getCode() {
    return Optional.ofNullable(this.code);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public MenuEntryType getType() {
    return this.type;
  }

  public void setType(MenuEntryType type) {
    this.type = type;
  }

  public Optional<String> getIcon() {
    return Optional.ofNullable(this.icon);
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Optional<String> getUrl() {
    return Optional.ofNullable(this.url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getIdFunction() {
    return this.idFunction;
  }

  public void setIdFunction(Long idFunction) {
    this.idFunction = idFunction;
  }

  public Optional<Long> getIdParent() {
    return Optional.ofNullable(this.idParent);
  }

  public void setIdParent(Long idParent) {
    this.idParent = idParent;
  }
}
