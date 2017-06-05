package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 *
 * @author sarath
 */
@Entity(name = "agent_lead_appointments")
public class AgentLeadAppointments extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "agnt_lead_appt_id", nullable = false)
	private Integer id;

	
	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;

 	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
 	
 	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id")
	private LeadMembership lead;
 
 	@Column(name = "appointment_time", nullable= true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar  appointmentTime;
 	
	/**
	 * 
	 */
	public AgentLeadAppointments() {
		super();
	}

	/**
	 * @param id
	 */
	public AgentLeadAppointments(final Integer id) {
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the lead
	 */
	public LeadMembership getLead() {
		return lead;
	}

	/**
	 * @param lead the lead to set
	 */
	public void setLead(LeadMembership lead) {
		this.lead = lead;
	}

	/**
	 * @return the appointmentTime
	 */
	public Calendar getAppointmentTime() {
		return appointmentTime;
	}

	/**
	 * @param appointmentTime the appointmentTime to set
	 */
	public void setAppointmentTime(Calendar appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AgentLeadAppointments)) {
			return false;
		}
		AgentLeadAppointments other = (AgentLeadAppointments) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Role[ id=" + id + " ]";
	}

}
