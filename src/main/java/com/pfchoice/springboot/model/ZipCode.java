package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 *
 * @author sarath
 */
@Entity
@Table(name = "lu_state_zip")
public class ZipCode extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "zipcode", nullable = false)
	private Integer code;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	/**
	 * 
	 */
	public ZipCode() {
		super();
	}

	/**
	 * @param code
	 */
	public ZipCode(final Integer code) {
		super();
		this.code = code;
	}

	/**
	 * @return
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(final Integer code) {
		this.code = code;
	}

	/**
	 * @return the stateCode
	 */
	public State getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(final State stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (code != null ? code.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ZipCode)) {
			return false;
		}
		ZipCode other = (ZipCode) object;
		if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.ZipCode[ code=" + code + " ]";
	}

}
