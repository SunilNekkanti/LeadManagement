package com.pfchoice.springboot.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 *
 * @author sarath
 */
@Entity(name = "lead_membership_claims")
public class LeadMembershipClaim extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_mbr_claim_id", nullable = false)
	private Integer id;

	
	@Column(name = "claim_id_number")
	private String claimNumber;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id", nullable=false)
	private LeadMembership leadMbr;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id")
	private Provider prvdr;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ins_id", referencedColumnName = "insurance_id")
	private Insurance ins;

	
	@Column(name = "claim_type")
	private String claimType;

	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "facility_type_code", referencedColumnName = "code")
	private FacilityType facilityType;

	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "bill_type_code", referencedColumnName = "code")
	private BillType billType;

	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "frequency_type_code", referencedColumnName = "code")
	private FrequencyType frequencyType;

	
	@Column(name = "bill_type")
	private String billTypec;
	
	
	@Column(name = "report_month")
	private Integer reportMonth;
	

	
	@Column(name = "dischargestatus")
	private String dischargeStatus;

	
	@Column(name = "memEnrollId")
	private String MemEnrollId;

	
	@Column(name = "diagnoses")
	private String diagnosis;

	
	
	@Column(name = "tin")
	private String tin;

	
	@Column(name = "dx_type_cd")
	private String dxTypeCode;

	
	@Column(name = "proc_type_cd")
	private String procTypeCode;

	
	@Column(name = "file_id")
	private Integer fileId;

	
	@OneToMany( fetch = FetchType.LAZY, cascade =  CascadeType.ALL ,mappedBy = "leadMbrClaim", orphanRemoval=true)
	private List<LeadMembershipClaimDetails> leadMbrClaimDetailsList;

	

	/**
	 * 
	 */
	public LeadMembershipClaim() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadMembershipClaim(final Integer id) {
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
	 * @return the claimNumber
	 */
	public String getClaimNumber() {
		return claimNumber;
	}

	/**
	 * @param claimNumber
	 *            the claimNumber to set
	 */
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	/**
	 * @return the mbr
	 */
	public LeadMembership getLeadMbr() {
		return leadMbr;
	}

	/**
	 * @param mbr
	 *            the mbr to set
	 */
	public void setLeadMbr(LeadMembership leadMbr) {
		this.leadMbr = leadMbr;
	}

	/**
	 * @return the prvdr
	 */
	public Provider getPrvdr() {
		return prvdr;
	}

	/**
	 * @param prvdr
	 *            the prvdr to set
	 */
	public void setPrvdr(Provider prvdr) {
		this.prvdr = prvdr;
	}

	/**
	 * @return the ins
	 */
	public Insurance getIns() {
		return ins;
	}

	/**
	 * @param ins
	 *            the ins to set
	 */
	public void setIns(Insurance ins) {
		this.ins = ins;
	}

	/**
	 * @return the claimType
	 */
	public String getClaimType() {
		return claimType;
	}

	/**
	 * @param claimType
	 *            the claimType to set
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**
	 * @return the frequencyType
	 */
	public FrequencyType getFrequencyType() {
		return frequencyType;
	}

	/**
	 * @param frequencyType
	 *            the frequencyType to set
	 */
	public void setFrequencyType(FrequencyType frequencyType) {
		this.frequencyType = frequencyType;
	}

	/**
	 * @return the billType
	 */
	public String getBillTypec() {
		return billTypec;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillTypec(String billTypec) {
		this.billTypec = billTypec;
	}

	/**
	 * @return the dischargeStatus
	 */
	public String getDischargeStatus() {
		return dischargeStatus;
	}

	/**
	 * @param dischargeStatus
	 *            the dischargeStatus to set
	 */
	public void setDischargeStatus(String dischargeStatus) {
		this.dischargeStatus = dischargeStatus;
	}

	/**
	 * @return the memEnrollId
	 */
	public String getMemEnrollId() {
		return MemEnrollId;
	}

	/**
	 * @param memEnrollId
	 *            the memEnrollId to set
	 */
	public void setMemEnrollId(String memEnrollId) {
		MemEnrollId = memEnrollId;
	}

	/**
	 * @return the diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * @param diagnosis
	 *            the diagnosis to set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * @param tin
	 *            the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * @return the dxTypeCode
	 */
	public String getDxTypeCode() {
		return dxTypeCode;
	}

	/**
	 * @param dxTypeCode
	 *            the dxTypeCode to set
	 */
	public void setDxTypeCode(String dxTypeCode) {
		this.dxTypeCode = dxTypeCode;
	}

	/**
	 * @return the procTypeCode
	 */
	public String getProcTypeCode() {
		return procTypeCode;
	}

	/**
	 * @param procTypeCode
	 *            the procTypeCode to set
	 */
	public void setProcTypeCode(String procTypeCode) {
		this.procTypeCode = procTypeCode;
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
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the mbrClaimDetailsList
	 */
	public List<LeadMembershipClaimDetails> getLeadMbrClaimDetailsList() {
		return leadMbrClaimDetailsList;
	}

	/**
	 * @param mbrClaimDetailsList
	 *            the mbrClaimDetailsList to set
	 */
	public void setMbrClaimDetailsList(List<LeadMembershipClaimDetails> leadMbrClaimDetailsList) {
		this.leadMbrClaimDetailsList = leadMbrClaimDetailsList;
	}

	/**
	 * @return the facilityType
	 */
	public FacilityType getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType
	 *            the facilityType to set
	 */
	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return the billType
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            the billType to set
	 */
	public void setBillType(BillType billType) {
		this.billType = billType;
	}
	
	/**
	 * @return
	 */
	public Integer getReportMonth() {
		return reportMonth;
	}

	/**
	 * @param reportMonth
	 */
	public void setReportMonth(Integer reportMonth) {
		this.reportMonth = reportMonth;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LeadMembershipClaim)) {
			return false;
		}
		LeadMembershipClaim other = (LeadMembershipClaim) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.MembershipClaim[ id=" + id + " ]";
	}

}
