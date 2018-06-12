package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pfchoice.springboot.util.JsonDateAndTimeDeserializer;
import com.pfchoice.springboot.util.JsonDateAndTimeSerializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sarath
 *
 */
@Entity
@Table(name = "event_assignment")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"representatives"})
@EqualsAndHashCode(callSuper =false,of = {"event","repeatRule","eventDateStartTime","eventDateEndTime"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class EventAssignment extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "event_assignment_id", nullable = false)
	private Integer id;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	private Event event;

	@Column(name = "repeat_rule")
	private String repeatRule;

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "event_date_starttime", nullable = true)
	private Date eventDateStartTime;

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "event_date_endtime", nullable = true)
	private Date eventDateEndTime;

	@Fetch(FetchMode.SELECT)
	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "event_assignment_representatives", joinColumns = {
			@JoinColumn(name = "event_assignment_id", referencedColumnName = "event_assignment_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) })
	private Set<User> representatives;

}
