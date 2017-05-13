package com.pfchoice.springboot.model;

import java.io.Serializable;
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

	@ManyToOne
	@JoinColumn(name = "lead_Mbr_CountyCode", referencedColumnName = "code")
	private County countyCode;

	@Column(name = "lead_Mbr_DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;

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

	@Column(name = "has_medicaid", insertable = false)
	private Character hasMedicaid;

	@Column(name = "has_medicare", insertable = false)
	private Character hasMedicare;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_Mbr_id", nullable = false)
	private Integer id;

	@Column(name = "lead_Mbr_LastName")
	private String lastName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipInsurance> leadMbrInsuranceList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leadMbr")
	private List<LeadMembershipProvider> leadMbrProviderList;

	@Column(name = "lead_Mbr_MedicaidNo")
	private String medicaidNo;

	@Column(name = "lead_Mbr_MedicareNo")
	private String medicareNo;

	@OneToMany(mappedBy = "leadMbr", fetch = FetchType.LAZY)
	private List<ReferenceContact> refContactList;

	@OneToOne
	@JoinColumn(name = "lead_Mbr_Status", referencedColumnName = "code")
	private MembershipStatus status;

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
	 * @return the refContactList
	 */
	public List<ReferenceContact> getRefContactList() {
		return refContactList;
	}

	/**
	 * @return the status
	 */
	public MembershipStatus getStatus() {
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
	 * @param refContactList
	 *            the refContactList to set
	 */
	public void setRefContactList(List<ReferenceContact> refContactList) {
		this.refContactList = refContactList;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final MembershipStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Membership[ id=" + id + " ]";
	}

}
