package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"stateCode" })
@EqualsAndHashCode(callSuper =false,exclude = {"id" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Contact extends RecordDetails implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "cnt_Id", nullable = false)
	private Integer id;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "home_phone")
	private String homePhone;

	@Column(name = "Extension")
	private Integer extension;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "fax_number")
	private String faxNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "city")
	private String city;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	@Column(name = "zipcode")
	private Integer zipCode;

	
	@Column(name = "file_id")
	private Integer fileId;
	
}
