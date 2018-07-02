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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Sarath Gandluri
 */
@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor
@ToString( exclude = { "languages" })
@EqualsAndHashCode(callSuper =false,exclude = {"id","languages"})

public class Provider extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "prvdr_Id", nullable = false)
	private Integer id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "provider_languages", joinColumns = {
			@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "language_id", referencedColumnName = "code", nullable = false, updatable = false) })
	public Set<Language> languages;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(cascade =   CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "provider_contacts", joinColumns = {
			@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "contact_id", referencedColumnName = "cnt_id", nullable = false, updatable = false, unique = true) })
	private Contact contact;

}
