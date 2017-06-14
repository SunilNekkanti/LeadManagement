package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@ManyToOne
	@JoinColumn(name = "brokerage_id", referencedColumnName = "code")
	private Brokerage brokerage;

	@ManyToOne
	@JoinColumn(name = "facility_type_id", referencedColumnName = "code")
	private FacilityType facilityType;
	
	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;
	
	@Column(name = "venue")
	private String venue;
	
	@Column(name = "event_date_time", nullable= true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar  eventDateTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User agent;

	

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
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the eventDateTime
	 */
	public Calendar getEventDateTime() {
		return eventDateTime;
	}

	/**
	 * @param eventDateTime the eventDateTime to set
	 */
	public void setEventDateTime(Calendar eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	/**
	 * @return the agent
	 */
	public User getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(User agent) {
		this.agent = agent;
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
