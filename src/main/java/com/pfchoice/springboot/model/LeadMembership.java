package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sarath
 *
 */
@Entity
@Table(name = "lead_membership")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LeadMembership extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_Mbr_id", nullable = false)
	private Integer id;

	@Column(name = "lead_Mbr_LastName")
	private String lastName;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lead")
	private List<AgentLeadAppointment> agentLeadAppointmentList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_type_id", referencedColumnName = "plan_type_id")
	private PlanType planType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ins_id", referencedColumnName = "insurance_id")
	private Insurance insurance;

	@ManyToOne
	@JoinColumn(name = "lead_Mbr_CountyCode", referencedColumnName = "code")
	private County countyCode;

	@Column(name = "lead_Mbr_DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name = "bestTimeToCall", nullable = true)
	private String bestTimeToCall;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_ethinic_code", referencedColumnName = "code")
	private Ethinicity ethinicCode;

	@Column(name = "file_id")
	private Integer fileId;

	@Column(name = "lead_Mbr_FirstName")
	private String firstName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_GenderID", referencedColumnName = "gender_id")
	private Gender gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_languageId", referencedColumnName = "code")
	private Language language;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_Status", referencedColumnName = "code", insertable = false)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipcode", referencedColumnName = "zipcode")
	private ZipCode zipCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	private Event event;

	@Column(name = "consent_form_signed")
	private Character consentFormSigned = new Character('N');

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_upload_id", referencedColumnName = "file_upload_id", nullable = false)
	private FileUpload fileUpload;

	@Column(name = "is_homeless")
	private Character isHomeless = new Character('N');

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lead")
	private List<LeadNotes> leadNotes;

	@javax.persistence.Transient
	private String notesHistory;

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
	public Gender getGender() {
		return gender;
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
	public void setGender(final Gender gender) {
		this.gender = gender;
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
	 * @param homePhone
	 *            the homePhone to set
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
	 * @param mobilePhone
	 *            the mobilePhone to set
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
	 * @param email
	 *            the email to set
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
	 * @param address1
	 *            the address1 to set
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
	 * @param address2
	 *            the address2 to set
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
	 * @param city
	 *            the city to set
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
	 * @param stateCode
	 *            the stateCode to set
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
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the language
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * @return the bestTimeToCall
	 */
	public String getBestTimeToCall() {
		return bestTimeToCall;
	}

	/**
	 * @param bestTimeToCall
	 *            the bestTimeToCall to set
	 */
	public void setBestTimeToCall(String bestTimeToCall) {
		this.bestTimeToCall = bestTimeToCall;
	}

	/**
	 * @return the agentLeadAppointmentList
	 */
	public List<AgentLeadAppointment> getAgentLeadAppointmentList() {
		return agentLeadAppointmentList;
	}

	/**
	 * @param agentLeadAppointmentList
	 *            the agentLeadAppointmentList to set
	 */
	public void setAgentLeadAppointmentList(List<AgentLeadAppointment> agentLeadAppointmentList) {
		for (AgentLeadAppointment agentLeadAppointment : agentLeadAppointmentList) {
			agentLeadAppointment.setLead(this);
			agentLeadAppointment.setCreatedBy("Sarath");
			agentLeadAppointment.setUpdatedBy("Sarath");
		}
		this.agentLeadAppointmentList = agentLeadAppointmentList;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the consentFormSigned
	 */
	public Character getConsentFormSigned() {
		return consentFormSigned;
	}

	/**
	 * @param consentFormSigned
	 *            the consentFormSigned to set
	 */
	public void setConsentFormSigned(Character consentFormSigned) {
		this.consentFormSigned = consentFormSigned;
	}

	/**
	 * @return the fileUpload
	 */
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload
	 *            the fileUpload to set
	 */
	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the planType
	 */
	public PlanType getPlanType() {
		return planType;
	}

	/**
	 * @param planType
	 *            the planType to set
	 */
	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	/**
	 * @return the isHomeless
	 */
	public Character getIsHomeless() {
		return isHomeless;
	}

	/**
	 * @param isHomeless
	 *            the isHomeless to set
	 */
	public void setIsHomeless(Character isHomeless) {
		this.isHomeless = isHomeless;
	}

	/**
	 * @return the insurance
	 */
	public Insurance getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the leadNotes
	 */
	public List<LeadNotes> getLeadNotes() {
		return leadNotes;
	}

	/**
	 * @param leadNotes
	 *            the leadNotes to set
	 */
	public void setLeadNotes(List<LeadNotes> leadNotes) {
		this.leadNotes = leadNotes;
	}

	/**
	 * @return the notesHistory
	 */
	public String getNotesHistory() {

		return leadNotes.stream().sorted(Comparator.comparing(LeadNotes::getCreatedDate).reversed())
				.map(LeadNotes::toString).collect(Collectors.joining(" "));

	}

	/**
	 * @param notesHistory
	 *            the notesHistory to set
	 */
	public void setNotesHistory(String notesHistory) {
		this.notesHistory = leadNotes.stream().map(ln -> ln.getNotes()).reduce("", String::concat);

	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.LeadMembership[ id=" + id + " ]";
	}

}
