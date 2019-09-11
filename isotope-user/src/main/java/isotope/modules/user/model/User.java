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
import isotope.commons.entities.IHistory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(
		name = "`user`"
)
public class User extends BaseEntity implements IHistory {
	@Column(
			name = "lastname",
			nullable = false,
			length = 255
	)
	private String lastname;

	@Column(
			name = "firstname",
			nullable = false,
			length = 255
	)
	private String firstname;

	@Column(
			name = "email",
			nullable = false,
			length = 255
	)
	private String email;

	@Column(
			name = "login",
			nullable = false,
			length = 50
	)
	private String login;

	@Column(
			name = "password_hash",
			nullable = false,
			length = 100
	)
	private String passwordHash;

	@Column(
			name = "phone_number",
			length = 20
	)
	private String phoneNumber;

	@Column(
			name = "company",
			length = 255
	)
	private String company;

	@Column(
			name = "disabled",
			nullable = false
	)
	private boolean disabled = false;

	@Column(
			name = "id_user_crea"
	)
	private Long idUserCrea;

	@Column(
			name = "id_user_mod"
	)
	private Long idUserMod;

	@Column(
			name = "date_creation",
			nullable = false
	)
	private LocalDateTime dateCreation;

	@Column(
			name = "date_modification"
	)
	private LocalDateTime dateModification;

	@JoinTable(name = "asso_user_group",
			joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "id_group", referencedColumnName = "id"))
	@ManyToMany
	private Set<Group> groups;

	@JoinTable(name = "is_asso_user_role",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_role"))
	@ManyToMany
	private Set<Role> roles;

	public User() {
	}

	@Override
	public Optional<Long> getIdUserCreation() {
		return getIdUserCrea();
	}

	public void setIdUserCreation(Long idUserCrea) {
		setIdUserCrea(idUserCrea);
	}

	@Override
	public Optional<Long> getIdUserModification() {
		return getIdUserMod();
	}

	public void setIdUserModification(Long idUserMod) {
		setIdUserMod(idUserMod);
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// Getters & Setters

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Optional<String> getPhoneNumber() {
		return Optional.ofNullable(this.phoneNumber);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Optional<String> getCompany() {
		return Optional.ofNullable(this.company);
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	private Optional<Long> getIdUserCrea() {
		return Optional.ofNullable(idUserCrea);
	}

	private void setIdUserCrea(Long idUserCrea) {
		this.idUserCrea = idUserCrea;
	}

	private Optional<Long> getIdUserMod() {
		return Optional.ofNullable(idUserMod);
	}

	private void setIdUserMod(Long idUserMod) {
		this.idUserMod = idUserMod;
	}

	@Override
	public LocalDateTime getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	public Optional<LocalDateTime> getDateModification() {
		return Optional.ofNullable(this.dateModification);
	}

	public void setDateModification(LocalDateTime dateModification) {
		this.dateModification = dateModification;
	}
}
