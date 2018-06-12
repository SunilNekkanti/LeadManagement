package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"facilityType","contact","attachments","eventAssignments"})
@EqualsAndHashCode(callSuper =false,of = {"eventName","eventDateStartTime","eventDateEndTime"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Event extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "event_id", nullable = false)
	private Integer id;

	@Column(name = "event_name")
	private String eventName;

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "event_date_starttime", nullable = true)
	private Date eventDateStartTime;

	@JsonSerialize(using = JsonDateAndTimeSerializer.class)
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "event_date_endtime", nullable = true)
	private Date eventDateEndTime;

	@ManyToOne
	@JoinColumn(name = "facility_type_id", referencedColumnName = "code")
	private FacilityType facilityType;

	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;

	@OneToOne(cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "event_contacts", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "contact_id", referencedColumnName = "cnt_id", nullable = false, updatable = false, unique = true) })
	private Contact contact;

	@Fetch(FetchMode.SELECT)
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "event_files_upload", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "files_upload_id", referencedColumnName = "file_upload_id", nullable = false, updatable = false) })
	private Set<FileUpload> attachments;

	@JsonIgnore
	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	private Set<EventAssignment> eventAssignments;

}
