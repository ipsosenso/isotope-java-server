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

package isotope.modules.cm.valuelist.model;

import isotope.commons.entities.BaseEntity;
import isotope.modules.cm.valuelistentry.model.ValueListEntry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(
		name = "is_cm_value_list"
)
public class ValueList extends BaseEntity {
	@Column(
			name = "name",
			nullable = false,
			length = 100
	)
	private String name;

	@Column(
			name = "shortcut",
			nullable = false,
			length = 50
	)
	private String shortcut;

	@Column(
			name = "description",
			length = 255
	)
	private String description;

	@Column(
			name = "creator_id"
	)
	private Long creatorId;

	@Column(
			name = "creator_name",
			nullable = false,
			length = 255
	)
	private String creatorName;

	@Column(
			name = "creation_date",
			nullable = false,
			updatable = false
	)
	private LocalDateTime creationDate;

	@Column(
			name = "modifier_id"
	)
	private Long modifierId;

	@Column(
			name = "modifier_name",
			nullable = false,
			length = 255
	)
	private String modifierName;

	@Column(
			name = "modification_date",
			nullable = false
	)
	private LocalDateTime modificationDate;

	@OneToMany(mappedBy = "valueList")
	private List<ValueListEntry> entryList;

	public ValueList() {
	}

	public List<ValueListEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<ValueListEntry> entryList) {
		this.entryList = entryList;
	}

	// Getters & Setters

	public String getName() {
		return this.name;
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

	public Optional<String> getDescription() {
		return Optional.ofNullable(this.description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Optional<Long> getCreatorId() {
		return Optional.ofNullable(this.creatorId);
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public LocalDateTime getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Optional<Long> getModifierId() {
		return Optional.ofNullable(this.modifierId);
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifierName() {
		return this.modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public LocalDateTime getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}
}
