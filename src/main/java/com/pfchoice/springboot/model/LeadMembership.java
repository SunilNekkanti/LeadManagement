package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author sarath
 *
 */
@Entity
@Table(name = "lead_membership")
public class LeadMembership extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_Mbr_id", nullable = false)
	private Integer id;

	@Column(name = "lead_Mbr_LastName")
	private String lastName;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipInsurance> leadMbrInsuranceList;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipProvider> leadMbrProviderList;

	@Column(name = "lead_Mbr_MedicaidNo")
	private String medicaidNo;

	@Column(name = "lead_Mbr_MedicareNo")
	private String medicareNo;

	@ManyToOne
	@JoinColumn(name = "lead_Mbr_CountyCode", referencedColumnName = "code")
	private County countyCode;

	@Column(name = "lead_Mbr_DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name = "bestTimeToCall", nullable= true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar  bestTimeToCall;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_ethinic_code", referencedColumnName = "code")
	private Ethinicity ethinicCode;

	@Column(name = "file_id")
	private Integer fileId;

	@Column(name = "lead_Mbr_FirstName")
	private String firstName;

	@ManyToOne
	@JoinColumn(name = "lead_Mbr_GenderID", referencedColumnName = "gender_id")
	private Gender genderId;

	@Column(name = "has_medicaid")
	private Character hasMedicaid = new Character('N');

	@Column(name = "has_medicare")
	private Character hasMedicare  = new Character('N');

	@Column(name = "has_disability")
	private Character hasDisability  = new Character('N') ;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lead_Mbr_languageId", referencedColumnName = "code")
	private LeadLanguage language;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lead_Mbr_Status", referencedColumnName = "code")
	private LeadStatus status;
	
	@Column(name = "home_phone")
	private String homePhone;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "email")
	private String email;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;

	@Column(name = "city")
	private String city;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "zipcode", referencedColumnName = "zipcode")
	private ZipCode zipCode;

	

	/**
	 * 
	 */
	public LeadMembership() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadMembership(final Integer id) {
		super();
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LeadMembership)) {
			return false;
		}
		LeadMembership other = (LeadMembership) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	/**
	 * @return the countyCode
	 */
	public County getCountyCode() {
		return countyCode;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @return the ethinicCode
	 */
	public Ethinicity getEthinicCode() {
		return ethinicCode;
	}

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the genderId
	 */
	public Gender getGenderId() {
		return genderId;
	}

	/**
	 * @return the hasMedicaid
	 */
	public Character getHasMedicaid() {
		return hasMedicaid;
	}

	/**
	 * @return the hasMedicare
	 */
	public Character getHasMedicare() {
		return hasMedicare;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return
	 */
	public List<LeadMembershipInsurance> getLeadMbrInsuranceList() {
		return leadMbrInsuranceList;
	}

	/**
	 * @return the mbrProviderList
	 */
	public List<LeadMembershipProvider> getLeadMbrProviderList() {
		return leadMbrProviderList;
	}

	/**
	 * @return the medicaidNo
	 */
	public String getMedicaidNo() {
		return medicaidNo;
	}

	/**
	 * @return the medicareNo
	 */
	public String getMedicareNo() {
		return medicareNo;
	}

	/**
	 * @return the status
	 */
	public LeadStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	/**
	 * @param countyCode
	 *            the countyCode to set
	 */
	public void setCountyCode(final County countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @param ethinicCode
	 *            the ethinicCode to set
	 */
	public void setEthinicCode(final Ethinicity ethinicCode) {
		this.ethinicCode = ethinicCode;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(final Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param genderId
	 *            the genderId to set
	 */
	public void setGenderId(final Gender genderId) {
		this.genderId = genderId;
	}

	/**
	 * @param hasMedicaid the hasMedicaid to set
	 */
	public void setHasMedicaid(Character hasMedicaid) {
		this.hasMedicaid = hasMedicaid;
	}

	/**
	 * @param hasMedicare the hasMedicare to set
	 */
	public void setHasMedicare(Character hasMedicare) {
		this.hasMedicare = hasMedicare;
	}

	/**
	 * @param id
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	
	/**
	 * @param leadMbrInsuranceList
	 */
	public void setLeadMbrInsuranceList(List<LeadMembershipInsurance> leadMbrInsuranceList) {
		this.leadMbrInsuranceList = leadMbrInsuranceList;
	}

	/**
	 * @param mbrProviderList
	 *            the mbrProviderList to set
	 */
	public void setLeadMbrProviderList(List<LeadMembershipProvider> leadMbrProviderList) {
		this.leadMbrProviderList = leadMbrProviderList;
	}

	/**
	 * @param medicaidNo
	 *            the medicaidNo to set
	 */
	public void setMedicaidNo(final String medicaidNo) {
		this.medicaidNo = medicaidNo;
	}

	/**
	 * @param medicareNo
	 *            the medicareNo to set
	 */
	public void setMedicareNo(final String medicareNo) {
		this.medicareNo = medicareNo;
	}

	/**
	 * @return the hasDisability
	 */
	public Character getHasDisability() {
		return hasDisability;
	}

	/**
	 * @param hasDisability the hasDisability to set
	 */
	public void setHasDisability(Character hasDisability) {
		this.hasDisability = hasDisability;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final LeadStatus status) {
		this.status = status;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the stateCode
	 */
	public State getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(State stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the zipCode
	 */
	public ZipCode getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the language
	 */
	public LeadLanguage getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(LeadLanguage language) {
		this.language = language;
	}

	
	/**
	 * @return the bestTimeToCall
	 */
	public Calendar getBestTimeToCall() {
		return bestTimeToCall;
	}

	/**
	 * @param bestTimeToCall the bestTimeToCall to set
	 */
	public void setBestTimeToCall(Calendar bestTimeToCall) {
		this.bestTimeToCall = bestTimeToCall;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.LeadMembership[ id=" + id + " ]";
	}

}
