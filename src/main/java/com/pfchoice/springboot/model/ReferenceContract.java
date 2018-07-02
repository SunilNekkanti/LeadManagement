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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pfchoice.springboot.model.Insurance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Sarath Gandluri
 */
@Entity(name = "reference_contract")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"ins","prvdr","contract"})

public class ReferenceContract extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ref_contract_Id", nullable = false)
	private Integer id;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "insurance_id", referencedColumnName = "insurance_id")
	private Insurance ins;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id")
	private Provider prvdr;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "referenceContract")
	private Contract contract;

}
