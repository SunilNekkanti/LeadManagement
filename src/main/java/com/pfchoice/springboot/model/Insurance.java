package com.pfchoice.springboot.model;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
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
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"planType","contact"})
@EqualsAndHashCode(callSuper =false,of = {"name"})
public class Insurance extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "Insurance_Id", nullable = false)
	private Integer id;

	@Column(name = "name")
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_Type_id", referencedColumnName = "plan_type_id")
	private PlanType planType;

	@OneToOne(optional= true, cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "insurance_contacts", joinColumns = {
			@JoinColumn(name = "ins_id", referencedColumnName = "Insurance_Id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "contact_id", referencedColumnName = "cnt_id", nullable = false, updatable = false, unique = true) })
	private Contact contact;


}
