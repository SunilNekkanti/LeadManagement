package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Sarath Gandluri
 */
@Entity
@Table(name = "lu_membership_status_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"description"})

public class LeadStatusDetail extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "code", columnDefinition = "TINYINT", nullable = false)
	private Short id;

	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "lu_membership_status_id", referencedColumnName = "code")
	private LeadStatus leadStatus;
	
}
