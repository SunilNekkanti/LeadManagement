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
import javax.persistence.OneToOne;



/**
 *
 * @author sarath
 */
@Entity(name = "contact")
public class Contact extends RecordDetails implements Serializable {

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

	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ref_cnt_id", referencedColumnName = "ref_cnt_id")
	private ReferenceContact refContact;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipcode", referencedColumnName = "zipcode")
	private ZipCode zipCode;

	
	@Column(name = "file_id")
	private Integer fileId;

	/**
	 * 
	 */
	public Contact() {
		super();
	}

	/**
	 * @param id
	 */
	public Contact(final Integer id) {
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
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone
	 *            the homePhone to set
	 */
	public void setHomePhone(final String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the extension
	 */
	public Integer getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(Integer extension) {
		this.extension = extension;
	}

	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            the mobilePhone to set
	 */
	public void setMobilePhone(final String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @param faxNumber
	 *            the faxNumber to set
	 */
	public void setFaxNumber(final String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(final String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(final String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public ZipCode getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(final ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the stateCode
	 */
	public State getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(final State stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(final Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the refContact
	 */
	public ReferenceContact getRefContact() {
		return refContact;
	}

	/**
	 * @param refContact
	 *            the refContact to set
	 */
	public void setRefContact(final ReferenceContact refContact) {
		this.refContact = refContact;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Contact)) {
			return false;
		}
		Contact other = (Contact) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Contact[ id=" + id + " ]";
	}

}
