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

package isotope.modules.cm.lang.lightbean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class LangLightBean {
  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long id;

  private String name;

  private String codeLang;

  private Boolean langueDefaut;

  private Boolean flagActifFo;

  private Boolean flagActifBo;

  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idCreator;

  private LocalDateTime creationDate;

  @JsonSerialize(
      using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class
  )
  private Long idModifier;

  private LocalDateTime modificationDate;

  public LangLightBean() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Boolean getLangueDefaut() {
    return this.langueDefaut;
  }

  public void setLangueDefaut(Boolean langueDefaut) {
    this.langueDefaut = langueDefaut;
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
