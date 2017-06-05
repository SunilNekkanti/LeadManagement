package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 *
 * @author Mohanasundharam
 */
@Entity
@Table(name = "lu_membership_status")
public class LeadStatus extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "code", nullable = false)
	private Byte id;

	
	@Column(name = "description")
	private String description;

	/**
	 * 
	 */
	public LeadStatus() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadStatus(final Byte id) {
		super();
		this.id = id;
	}

	/**
	 * @return
	 */
	public Byte getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(final Byte id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LeadStatus)) {
			return false;
		}
		LeadStatus other = (LeadStatus) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.MembershipStatus[ id=" + id + " ]";
	}

}
