package com.pfchoice.springboot.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;



/**
 * @author sarath
 *
 */
@Component
public class StatusReportDTO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String lastName;

	private String planType;

	private String initialInsurance;

	private String firstName;

	private String gender;

	private String language;

	private String bestTimeToCall;

	private String status;
	
	private String statusDetail;

	private String contact;

	private String event;

	private Character consentFormSigned ;

	private String fileUpload;

	private String notesHistory;
	
	private Integer count;
	
	private String userName;

	/**
	 * 
	 */
	public StatusReportDTO() {
		super();
	}

	/**
	 * @param id
	 */
	public StatusReportDTO(final Integer id) {
		super();
		this.id = id;
	}

	
	public StatusReportDTO(String lastName, String planType, String initialInsurance, String firstName, String status,
			String event, Integer count, String userName) {
		super();
		this.lastName = lastName;
		this.planType = planType;
		this.initialInsurance = initialInsurance;
		this.firstName = firstName;
		this.status = status;
		this.event = event;
		this.count = count;
		this.userName = userName;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * @return the initialInsurance
	 */
	public String getInitialInsurance() {
		return initialInsurance;
	}

	/**
	 * @param initialInsurance the initialInsurance to set
	 */
	public void setInitialInsurance(String initialInsurance) {
		this.initialInsurance = initialInsurance;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the bestTimeToCall
	 */
	public String getBestTimeToCall() {
		return bestTimeToCall;
	}

	/**
	 * @param bestTimeToCall the bestTimeToCall to set
	 */
	public void setBestTimeToCall(String bestTimeToCall) {
		this.bestTimeToCall = bestTimeToCall;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusDetail
	 */
	public String getStatusDetail() {
		return statusDetail;
	}

	/**
	 * @param statusDetail the statusDetail to set
	 */
	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the consentFormSigned
	 */
	public Character getConsentFormSigned() {
		return consentFormSigned;
	}

	/**
	 * @param consentFormSigned the consentFormSigned to set
	 */
	public void setConsentFormSigned(Character consentFormSigned) {
		this.consentFormSigned = consentFormSigned;
	}

	/**
	 * @return the fileUpload
	 */
	public String getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the notesHistory
	 */
	public String getNotesHistory() {
		return notesHistory;
	}

	/**
	 * @param notesHistory the notesHistory to set
	 */
	public void setNotesHistory(String notesHistory) {
		this.notesHistory = notesHistory;
	}
	
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof StatusReportDTO)) {
			return false;
		}
		StatusReportDTO other = (StatusReportDTO) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	 
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

}
