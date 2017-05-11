package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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



/**
 *
 * @author sarath
 */
/**
 * @author MS
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

	
	@Column(name = "lead_Mbr_FirstName")
	private String firstName;

	
	@Column(name = "lead_Mbr_LastName")
	private String lastName;

	
	@ManyToOne
	@JoinColumn(name = "lead_Mbr_GenderID", referencedColumnName = "gender_id")
	private Gender genderId;

	
	@ManyToOne
	@JoinColumn(name = "lead_Mbr_CountyCode", referencedColumnName = "code")
	private County countyCode;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_ethinic_code", referencedColumnName = "code")
	private Ethinicity ethinicCode;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipProvider> leadMbrProviderList;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipInsurance> leadMbrInsuranceList;

	
	@Column(name = "lead_Mbr_DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;

	
	@Column(name = "lead_Mbr_MedicaidNo")
	private String medicaidNo;

	
	@Column(name = "lead_Mbr_MedicareNo")
	private String medicareNo;

	
	@Column(name = "file_id")
	private Integer fileId;

	
	@OneToOne
	@JoinColumn(name = "lead_Mbr_Status", referencedColumnName = "code")
	private MembershipStatus status;

	
	@OneToMany(mappedBy = "leadMbr", fetch = FetchType.LAZY)
	private List<ReferenceContact> refContactList; 

	
	@OneToMany(mappedBy = "leadMbr", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LeadMembershipHedisMeasure> leadMbrHedisMeasureList;

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the genderId
	 */
	public Gender getGenderId() {
		return genderId;
	}

	/**
	 * @param genderId
	 *            the genderId to set
	 */
	public void setGenderId(final Gender genderId) {
		this.genderId = genderId;
	}

	/**
	 * @return the countyCode
	 */
	public County getCountyCode() {
		return countyCode;
	}

	/**
	 * @param countyCode
	 *            the countyCode to set
	 */
	public void setCountyCode(final County countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @return the mbrProviderList
	 */
	public List<LeadMembershipProvider> getLeadMbrProviderList() {
		return leadMbrProviderList;
	}

	/**
	 * @param mbrProviderList
	 *            the mbrProviderList to set
	 */
	public void setLeadMbrProviderList(List<LeadMembershipProvider> leadMbrProviderList) {
		this.leadMbrProviderList = leadMbrProviderList;
	}

	
	/**
	 * @return
	 */
	public List<LeadMembershipInsurance> getLeadMbrInsuranceList() {
		return leadMbrInsuranceList;
	}

	/**
	 * @param leadMbrInsuranceList
	 */
	public void setLeadMbrInsuranceList(List<LeadMembershipInsurance> leadMbrInsuranceList) {
		this.leadMbrInsuranceList = leadMbrInsuranceList;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the ethinicCode
	 */
	public Ethinicity getEthinicCode() {
		return ethinicCode;
	}

	/**
	 * @param ethinicCode
	 *            the ethinicCode to set
	 */
	public void setEthinicCode(final Ethinicity ethinicCode) {
		this.ethinicCode = ethinicCode;
	}

	/**
	 * @return the status
	 */
	public MembershipStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final MembershipStatus status) {
		this.status = status;
	}

	/**
	 * @return the medicaidNo
	 */
	public String getMedicaidNo() {
		return medicaidNo;
	}

	/**
	 * @param medicaidNo
	 *            the medicaidNo to set
	 */
	public void setMedicaidNo(final String medicaidNo) {
		this.medicaidNo = medicaidNo;
	}

	/**
	 * @return the medicareNo
	 */
	public String getMedicareNo() {
		return medicareNo;
	}

	/**
	 * @param medicareNo
	 *            the medicareNo to set
	 */
	public void setMedicareNo(final String medicareNo) {
		this.medicareNo = medicareNo;
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
	 * @return
	 */
	/*public List<Contact> getContactList() {
		return contactList;
	}*/

	/**
	 * @param contactList
	 */
	/*public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}*/
	
	/**
	 * @return
	 */
	public List<LeadMembershipHedisMeasure> getLeadMbrHedisMeasureList() {
		return leadMbrHedisMeasureList;
	}

	/**
	 * @param leadMbrHedisMeasureList
	 */
	public void setLeadMbrHedisMeasureList(List<LeadMembershipHedisMeasure> leadMbrHedisMeasureList) {
		this.leadMbrHedisMeasureList = leadMbrHedisMeasureList;
	}

	/**
	 * @return the refContactList
	 */
	public List<ReferenceContact> getRefContactList() {
		return refContactList;
	}

	/**
	 * @param refContactList the refContactList to set
	 */
	public void setRefContactList(List<ReferenceContact> refContactList) {
		this.refContactList = refContactList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
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

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Membership[ id=" + id + " ]";
	}

}
