package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 *
 * @author sarath
 */
@Entity(name = "agent_lead_appointments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AgentLeadAppointment extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "agnt_lead_appt_id", nullable = false)
	private Integer id;

	
	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;
	
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
 	
 	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id")
	private LeadMembership lead;
 
 	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="America/NewYork")
 	@Column(name = "appointment_time", nullable= true)
	private Calendar  appointmentTime;
 	
 	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id")
	private Provider prvdr;
 	
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "plan_type_id", referencedColumnName = "plan_type_id")
	private PlanType planType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ins_id", referencedColumnName = "insurance_id")
	private Insurance insurance;

	@Column(name = "effective_from")
	@Temporal(TemporalType.DATE)
	private Date effectiveFrom;
	
	@Column(name = "transportation", insertable = false)
	private Character transportation = new Character('N');
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="America/NewYork")
 	@Column(name = "dr_appointment_time")
	private Calendar  drAppointmentTime;
	
	/**
	 * 
	 */
	public AgentLeadAppointment() {
		super();
	}

	/**
	 * @param id
	 */
	public AgentLeadAppointment(final Integer id) {
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

	/**
	 * @return the prvdr
	 */
	public Provider getPrvdr() {
		return prvdr;
	}

	/**
	 * @param prvdr the prvdr to set
	 */
	public void setPrvdr(Provider prvdr) {
		this.prvdr = prvdr;
	}

	/**
	 * @return the planType
	 */
	public PlanType getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	/**
	 * @return the insurance
	 */
	public Insurance getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the effectiveFrom
	 */
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	/**
	 * @param effectiveFrom the effectiveFrom to set
	 */
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	/**
	 * @return the transportation
	 */
	public Character getTransportation() {
		return transportation;
	}

	/**
	 * @param transportation the transportation to set
	 */
	public void setTransportation(Character transportation) {
		this.transportation = transportation;
	}

	/**
	 * @return the drAppointmentTime
	 */
	public Calendar getDrAppointmentTime() {
		return drAppointmentTime;
	}

	/**
	 * @param drAppointmentTime the drAppointmentTime to set
	 */
	public void setDrAppointmentTime(Calendar drAppointmentTime) {
		this.drAppointmentTime = drAppointmentTime;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AgentLeadAppointment)) {
			return false;
		}
		AgentLeadAppointment other = (AgentLeadAppointment) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.AgentLeadAppointment[ id=" + id + " user_id = "+user.getId()+" ]";
	}

}
