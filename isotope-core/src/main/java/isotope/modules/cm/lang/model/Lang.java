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

package isotope.modules.cm.lang.model;


import isotope.commons.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "is_web_lang"
)
public class Lang extends BaseEntity {
  @Column(
      name = "name",
      nullable = false,
      length = 100
  )
  private String name;

  @Column(
      name = "code_lang",
      nullable = false,
      length = 5
  )
  private String codeLang;

  @Column(
      name = "langue_defaut_fo"
  )
  private Boolean langueDefautFo;

  @Column(
      name = "langue_defaut_bo"
  )
  private Boolean langueDefautBo;

  @Column(
      name = "flag_actif_fo"
  )
  private Boolean flagActifFo;

  @Column(
      name = "flag_actif_bo",
      nullable = false
  )
  private Boolean flagActifBo;

  @Column(
      name = "id_creator",
      nullable = false
  )
  private Long idCreator;

  @Column(
      name = "creation_date",
      nullable = false,
      updatable = false
  )
  private LocalDateTime creationDate;

  @Column(
      name = "id_modifier"
  )
  private Long idModifier;

  @Column(
      name = "modification_date",
      nullable = false
  )
  private LocalDateTime modificationDate;

  public Lang() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCodeLang() {
    return this.codeLang;
  }

  public void setCodeLang(String codeLang) {
    this.codeLang = codeLang;
  }

  public Boolean getLangueDefautFo() {
    return langueDefautFo;
  }

  public void setLangueDefautFo(Boolean langueDefautFo) {
    this.langueDefautFo = langueDefautFo;
  }

  public Boolean getLangueDefautBo() {
    return langueDefautBo;
  }

  public void setLangueDefautBo(Boolean langueDefautBo) {
    this.langueDefautBo = langueDefautBo;
  }

  public Boolean getFlagActifFo() {
    return this.flagActifFo;
  }

  public void setFlagActifFo(Boolean flagActifFo) {
    this.flagActifFo = flagActifFo;
  }

  public Boolean getFlagActifBo() {
    return this.flagActifBo;
  }

  public void setFlagActifBo(Boolean flagActifBo) {
    this.flagActifBo = flagActifBo;
  }

  public Long getIdCreator() {
    return this.idCreator;
  }

  public void setIdCreator(Long idCreator) {
    this.idCreator = idCreator;
  }

  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Long getIdModifier() {
    return this.idModifier;
  }

  public void setIdModifier(Long idModifier) {
    this.idModifier = idModifier;
  }

  public LocalDateTime getModificationDate() {
    return this.modificationDate;
  }

  public void setModificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
  }
}
