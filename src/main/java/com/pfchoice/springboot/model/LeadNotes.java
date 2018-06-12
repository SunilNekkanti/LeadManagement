package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author sarath
 */
@Entity
@Table(name = "lead_notes")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false, exclude={"id"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LeadNotes extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_notes_id", nullable = false)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_mbr_id", referencedColumnName = "lead_mbr_id", nullable = false)
	private LeadMembership lead;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@Column(name = "notes", length = 65535, columnDefinition = "TEXT")
	private String notes;

	@Override
	public String toString() {
		return this.getCreatedDate() + ">>>" + this.getCreatedBy() + ">>>>" + this.notes + "\n";
	}

}
