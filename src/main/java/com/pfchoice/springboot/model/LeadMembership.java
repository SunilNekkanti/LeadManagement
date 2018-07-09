package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pfchoice.springboot.util.JsonDateDeserializer;
import com.pfchoice.springboot.util.JsonDateSerializer;

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
@Table(name = "lead_membership")
@NoArgsConstructor
@ToString( exclude = { "leadNotes","agentLeadAppointmentList", "notesHistory","contact"})
@EqualsAndHashCode(callSuper =false,exclude = {"id","leadNotes","agentLeadAppointmentList","notesHistory","contact"})
@SqlResultSetMapping(
	    name="statusReportDTOMapping",
	    classes={
	        @ConstructorResult(
	            targetClass=StatusReportDTO.class, 
	            columns = { 
	            		 @ColumnResult(name = "lastName",type = String.class),
	            		 @ColumnResult(name = "firstName",type = String.class),
	            		 @ColumnResult(name = "initialInsurance",type = String.class),
	            		 @ColumnResult(name = "planType",type = String.class),
	            	     @ColumnResult(name = "status",type = String.class),
	            		 @ColumnResult(name = "event",type = String.class),
	            		 @ColumnResult(name = "count",type = Integer.class),
	            		 @ColumnResult(name = "userName",type = String.class),
	            		 @ColumnResult(name = "notes",type = String.class) 
	             }	            
	        )
	    }
	)
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "leadStatusReport",  procedureName = "LEAD_STATUS_REPORT", 
				 resultSetMappings="statusReportDTOMapping",parameters = {
				@StoredProcedureParameter(name = "usrName", type = String.class),
				@StoredProcedureParameter(name = "roleIds", type = String.class),
				@StoredProcedureParameter(name = "statusIds", type = String.class),
				@StoredProcedureParameter(name = "eventIds", type = String.class),
				@StoredProcedureParameter(name = "starttDate", type = String.class),
				@StoredProcedureParameter(name = "enddDate", type = String.class),
				@StoredProcedureParameter(name = "reportType", type = String.class) 
				})
		})

public class LeadMembership extends  RecordDetails  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_Mbr_id", nullable = false)
	@Getter
	@Setter
	private Integer id;

	@Column(name = "lead_Mbr_LastName")
	@Getter
	@Setter
	private String lastName;

	@Fetch(FetchMode.SELECT)
	@OneToMany(cascade =  CascadeType.ALL , fetch = FetchType.LAZY, mappedBy = "lead")
	@OrderBy("createdDate desc")
	@Getter
	@Setter
	private List<AgentLeadAppointment> agentLeadAppointmentList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "insurance_type_id", referencedColumnName = "plan_type_id")
	@Getter
	@Setter
	private PlanType planType;

	@Column(name = "present_insurance")
	@Getter
	@Setter
	private String initialInsurance;

	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@Column(name = "lead_Mbr_DOB")
	@Getter
	@Setter
	private Date dob;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_ethinic_code", referencedColumnName = "code")
	@Getter
	@Setter
	private Ethinicity ethinicCode;

	@Column(name = "file_id")
	@Getter
	@Setter
	private Integer fileId;

	@Column(name = "lead_Mbr_FirstName")
	@Getter
	@Setter
	private String firstName;

	@ManyToOne(fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinColumn(name = "lead_Mbr_GenderID", referencedColumnName = "gender_id")
	private Gender gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_Mbr_languageId", referencedColumnName = "code")
	@Getter
	@Setter
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinColumn(name = "best_time_to_call_id", referencedColumnName = "code")
	private BestTimeToCall bestTimeToCall;

	@OneToOne(fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinColumn(name = "lead_Mbr_Status", referencedColumnName = "code")
	private LeadStatus status;
	
	@OneToOne(optional = true,fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinColumn(name = "lead_mbr_status_detail_id", referencedColumnName = "code")
	private LeadStatusDetail statusDetail;
	
	
	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinTable(name = "lead_contacts", joinColumns = {
			@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id", nullable = false ) }, inverseJoinColumns = {
					@JoinColumn(name = "contact_id", referencedColumnName = "cnt_id", nullable = false, unique = true) })
	private Contact contact;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	@Getter
	@Setter
	private Event event;
	
	@Column(name = "consent_form_signed")
	@Getter
	@Setter
	private Character consentFormSigned = new Character('N');

	@OneToOne(optional = true,fetch = FetchType.LAZY)
	@Getter
	@Setter
	@JoinColumn(name = "file_upload_id", referencedColumnName = "file_upload_id", nullable = false)
	private FileUpload fileUpload;
	
	@Fetch(FetchMode.SELECT)
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lead")
	@OrderBy("createdDate desc")
	private List<LeadNotes> leadNotes;

	@javax.persistence.Transient
	private String notesHistory;

	@javax.persistence.Transient
	private String lastNotes;
	
	@PrePersist
    public void onPrePersist() {
       if (status == null) {
    	   status = new LeadStatus(new Short((short) 1));
       }
       
    }

	/**
	 * @return the notesHistory
	 */
	public String getNotesHistory() {

		return leadNotes.stream().sorted(Comparator.comparing(LeadNotes::getCreatedDate).reversed())
				.map(LeadNotes::toString).collect(Collectors.joining("\r\n"));

	}
	
	/**
	 * @return the notesHistory
	 */
	public String getLastNotes() {
		return (leadNotes.stream().findFirst().isPresent())?  leadNotes.stream().findFirst().get().toString():"";

	}
}
