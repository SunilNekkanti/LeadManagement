package com.pfchoice.springboot.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pfchoice.springboot.util.JsonDateAndTimeDeserializer;
import com.pfchoice.springboot.util.JsonDateAndTimeSerializer;
import com.pfchoice.springboot.util.JsonDateDeserializer;
import com.pfchoice.springboot.util.JsonDateSerializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity(name = "agent_lead_appointments")
@Getter
@Setter
@NoArgsConstructor
@ToString( exclude = {"user", "lead", "prvdr","planType","insurance"})
@EqualsAndHashCode(callSuper =false,of = {"lead","appointmentTime"})

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
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
	private User user;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id", nullable=false)
	private LeadMembership lead;

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "appointment_time", nullable = true)
	private Date appointmentTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id")
	private Provider prvdr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_type_id", referencedColumnName = "plan_type_id")
	private PlanType planType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ins_id", referencedColumnName = "insurance_id")
	private Insurance insurance;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@Column(name = "effective_from")
	private Date effectiveFrom;

	@Column(name = "transportation", insertable = false)
	private Character transportation = new Character('N');

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "dr_appointment_time")
	private Date drAppointmentTime;
}
