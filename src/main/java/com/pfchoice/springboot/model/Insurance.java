package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 *
 * @author Mohanasundharam
 */
@Entity
@Table(name = "insurance")
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
	@JoinColumn(name = "plan_type_id", referencedColumnName = "plan_type_id")
	private PlanType planTypeId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ins")
	private Set<ReferenceContact> refInsContacts = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prvdr")
	private Set<ReferenceContract> refContracts = new HashSet<>();
	
	/**
	 * 
	 */
	public Insurance() {
		super();
	}

	/**
	 * @param id
	 */
	public Insurance(final Integer id) {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the planTypeId
	 */
	public PlanType getPlanTypeId() {
		return planTypeId;
	}

	/**
	 * @param planTypeId
	 *            the planTypeId to set
	 */
	public void setPlanTypeId(PlanType planTypeId) {
		this.planTypeId = planTypeId;
	}

	/**
	 * @return the refInsContacts
	 */
	public Set<ReferenceContact> getRefInsContacts() {
		return refInsContacts;
	}

	/**
	 * @param refInsContacts
	 *            the refInsContacts to set
	 */
	public void setRefInsContacts(Set<ReferenceContact> refInsContacts) {
		this.refInsContacts = refInsContacts;
	}

	
	/**
	 * @return refContracts
	 */
	public Set<ReferenceContract> getRefContracts() {
		return refContracts;
	}

	/**
	 * @param refContracts
	 */
	public void setRefContracts(Set<ReferenceContract> refContracts) {
		this.refContracts = refContracts;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Insurance)) {
			return false;
		}
		Insurance other = (Insurance) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.Insurance[ id=" + id + " ]";
	}

}
