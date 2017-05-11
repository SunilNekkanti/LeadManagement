package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Entity
@Table(name = "membership")
public class Membership extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "mbr_id", nullable = false)
	private Integer id;

	
	@Column(name = "mbr_firstname")
	private String firstName;

	
	@Column(name = "mbr_lastname")
	private String lastName;

	
	@ManyToOne
	@JoinColumn(name = "mbr_genderid", referencedColumnName = "gender_id")
	private Gender genderId;

	
	@ManyToOne
	@JoinColumn(name = "mbr_countycode", referencedColumnName = "code")
	private County countyCode;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mbr_ethinic_code", referencedColumnName = "code")
	private Ethinicity ethinicCode;

	@Column(name = "mbr_dob")
	@Temporal(TemporalType.DATE)
	private Date dob;

	
	@Column(name = "mbr_medicaidno")
	private String medicaidNo;

	
	@Column(name = "mbr_medicareno")
	private String medicareNo;

	
	@Column(name = "file_id")
	private Integer fileId;

	
	@OneToOne
	@JoinColumn(name = "mbr_status", referencedColumnName = "code")
	private MembershipStatus status;

	
	
	@OneToMany(mappedBy = "mbr", fetch = FetchType.LAZY)
	private List<MembershipActivityMonth> mbrActivityMonthList;
	
	@OneToMany(mappedBy = "mbr", fetch = FetchType.LAZY)
	private List<ReferenceContact> refContactList; 

	/**
	 * 
	 */
	public Membership() {
		super();
	}

	/**
	 * @param id
	 */
	public Membership(final Integer id) {
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
	public List<MembershipActivityMonth> getMbrActivityMonthList() {
		return mbrActivityMonthList;
	}

	/**
	 * @param mbrActivityMonthList
	 */
	public void setMbrActivityMonthList(List<MembershipActivityMonth> mbrActivityMonthList) {
		this.mbrActivityMonthList = mbrActivityMonthList;
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
		if (!(object instanceof Membership)) {
			return false;
		}
		Membership other = (Membership) object;
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
