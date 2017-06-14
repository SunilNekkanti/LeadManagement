package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author sarath
 */
@Entity
@Table(name = "lu_state")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class State extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "code", nullable = false)
	private Integer code;

	
	@Column(name = "description")
	private String description;

	
	@Column(name = "shot_name")
	private String shortName;

	@OneToMany(mappedBy = "stateCode", fetch = FetchType.LAZY)
	private Set<ZipCode> zipCodes;

	/**
	 * 
	 */
	public State() {
		super();
	}

	/**
	 * @param code
	 */
	public State(final Integer code) {
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

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the zipCode
	 */
	public Set<ZipCode> getZipCodes() {
		return zipCodes;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCodes(final Set<ZipCode> zipCodes) {
		this.zipCodes = zipCodes;
	}


	@Override
	public boolean equals(Object object) {
		if (!(object instanceof State)) {
			return false;
		}
		State other = (State) object;
		if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.State[ code=" + code + " ]";
	}

}
