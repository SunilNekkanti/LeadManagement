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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Column(name = "event_date_starttime", nullable= true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar  eventDateStartTime;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Column(name = "event_date_endtime", nullable= true)
	@Temporal(TemporalType.TIMESTAMP)
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
	@JoinColumn(name = "event_template_id", referencedColumnName = "event_template_id")
	private EventTemplate eventTemplate;
	
	@ManyToOne
	@JoinColumn(name = "event_activity_type_id", referencedColumnName = "code")
	private ActivityType activityType;
	

	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "event_representatives", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "event_id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false, updatable = false) })
	public Set<User> representatives;
	
	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
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
	 * @return the eventTemplate
	 */
	public EventTemplate getEventTemplate() {
		return eventTemplate;
	}

	/**
	 * @param eventTemplate the eventTemplate to set
	 */
	public void setEventTemplate(EventTemplate eventTemplate) {
		this.eventTemplate = eventTemplate;
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
