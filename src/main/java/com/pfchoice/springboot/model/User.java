package com.pfchoice.springboot.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author sarath
 */
@Entity(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "Id", nullable = false)
	private Integer id;

	
	@Column(name = "username")
	private String username;

	
	@Column(name = "password")
	private String password;
	
	

	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id",nullable = false, updatable = false) })
	public Set<Role> roles;

	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "user_counties", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "county_id", referencedColumnName = "code",nullable = false, updatable = false) })
	private Set<County> counties;
	
	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "user_brokerages", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "brokerage_id", referencedColumnName = "code",nullable = false, updatable = false) })
	public Set<Brokerage> brokerages;
	
	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "user_languages", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "language_id", referencedColumnName = "code",nullable = false, updatable = false) })
	public Set<Language> languages;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "license_no")
	private String licenseNo;
	
	@Column(name = "position")
	private String position;
	
	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * @param id
	 */
	public User(final Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the counties
	 */
	public Set<County> getCounties() {
		return counties;
	}

	/**
	 * @param counties the counties to set
	 */
	public void setCounties(Set<County> counties) {
		this.counties = counties;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the licenseNo
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * @param licenseNo the licenseNo to set
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * @return the languages
	 */
	public Set<Language> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	/**
	 * @return the brokerages
	 */
	public Set<Brokerage> getBrokerages() {
		return brokerages;
	}

	/**
	 * @param brokerages the brokerages to set
	 */
	public void setBrokerages(Set<Brokerage> brokerages) {
		this.brokerages = brokerages;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.User[ id=" + id + "roles"+ roles.toString()+"  ]";
	}

}
