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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 *
 * @author Mohanasundharam
 */
@Entity
@Table(name = "insurance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	public PlanType getPlanType() {
		return planType;
	}

	/**
	 * @param planTypeId
	 *            the planTypeId to set
	 */
	public void setPlanType(PlanType planType) {
		this.planType = planType;
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
