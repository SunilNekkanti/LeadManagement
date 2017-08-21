package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sarath
 *
 */
@Entity
@Table(name = "event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "event_id", nullable = false)
	private Integer id;

	@Column(name = "event_name")
	private String eventName;

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="America/NewYork")
	@Column(name = "event_date_starttime", nullable= true)
	private Calendar  eventDateStartTime;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="America/NewYork")
	@Column(name = "event_date_endtime", nullable= true)
	private Calendar  eventDateEndTime;
	
	@ManyToOne
	@JoinColumn(name = "brokerage_id", referencedColumnName = "code")
	private Brokerage brokerage;

	@ManyToOne
	@JoinColumn(name = "facility_type_id", referencedColumnName = "code")
	private FacilityType facilityType;
	
	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;
	
	@ManyToOne
	@JoinColumn(name = "event_activity_type_id", referencedColumnName = "code")
	private ActivityType activityType;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;

	@Column(name = "city")
	private String city;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state", referencedColumnName = "code")
	private State state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zipcode", referencedColumnName = "zipcode")
	private ZipCode zipCode;
	
	@Column(name = "contact_person")
	private String contactPerson;
	
	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "contact_email")
	private String contactEmail;
	
	@Column(name = "repeat_rule")
	private String repeatRule;
	
	
	@ManyToMany(cascade= {CascadeType.MERGE,CascadeType.REMOVE} ,fetch = FetchType.LAZY)
	@JoinTable(name = "event_representatives", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "event_id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) })
	private Set<User> representatives;
	
	@ManyToMany( cascade= {CascadeType.MERGE,CascadeType.REMOVE} ,fetch = FetchType.LAZY)
	@JoinTable(name = "event_files_upload", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "event_id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "files_upload_id", referencedColumnName = "file_upload_id",nullable = false, updatable = false) })
	private Set<FileUpload> attachments;
	

	/**
	 * 
	 */
	public Event() {
		super();
	}

	/**
	 * @param id
	 */
	public Event(final Integer id) {
		super();
		this.id = id;
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
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the brokerage
	 */
	public Brokerage getBrokerage() {
		return brokerage;
	}

	/**
	 * @param brokerage the brokerage to set
	 */
	public void setBrokerage(Brokerage brokerage) {
		this.brokerage = brokerage;
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
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
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
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactPhone
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return the facilityType
	 */
	public FacilityType getFacilityType() {
		return facilityType;
	}

	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the eventDateStartTime
	 */
	public Calendar getEventDateStartTime() {
		return eventDateStartTime;
	}

	/**
	 * @param eventDateStartTime the eventDateStartTime to set
	 */
	public void setEventDateStartTime(Calendar eventDateStartTime) {
		this.eventDateStartTime = eventDateStartTime;
	}

	/**
	 * @return the eventDateEndTime
	 */
	public Calendar getEventDateEndTime() {
		return eventDateEndTime;
	}

	/**
	 * @param eventDateEndTime the eventDateEndTime to set
	 */
	public void setEventDateEndTime(Calendar eventDateEndTime) {
		this.eventDateEndTime = eventDateEndTime;
	}


	/**
	 * @return the activityType
	 */
	public ActivityType getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the repeatRule
	 */
	public String getRepeatRule() {
		return repeatRule;
	}

	/**
	 * @param repeatRule the repeatRule to set
	 */
	public void setRepeatRule(String repeatRule) {
		this.repeatRule = repeatRule;
	}

	/**
	 * @return the representatives
	 */
	public Set<User> getRepresentatives() {
		return representatives;
	}

	/**
	 * @param representatives the representatives to set
	 */
	public void setRepresentatives(Set<User> representatives) {
		this.representatives = representatives;
	}

	/**
	 * @return the attachments
	 */
	public Set<FileUpload> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(Set<FileUpload> attachments) {
		this.attachments = attachments;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Event)) {
			return false;
		}
		Event other = (Event) object;
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


	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Event[ id=" + id + " ]";
	}

}
