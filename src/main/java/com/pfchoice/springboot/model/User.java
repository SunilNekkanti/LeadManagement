package com.pfchoice.springboot.model;

import java.io.Serializable;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"language","insurance", "contact", "role"})
@EqualsAndHashCode(callSuper =false,exclude = {"id"})
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "Id", nullable = false)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id", referencedColumnName = "code")
	private Language language;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_insurances", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "insurance_id", referencedColumnName = "insurance_id", nullable = false) })
	private  Insurance insurance;

	@OneToOne(cascade =    CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_contacts", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "contact_id", referencedColumnName = "cnt_id", nullable = false, unique = true) })
	private Contact contact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	@Column(name = "license_no")
	private String licenseNo;

}
