package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author Mohanasundharam
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "provider")
public class Provider extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "prvdr_Id", unique = true, nullable = false)
	private Integer id;

	
	@Column(name = "code")
	private String code;

	
	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "hours_of_operation")
	private String hoursOfOperation;
	
	@Column(name = "age_seen")
	private String ageSeen;
	
	@Column(name = "address")
	private String address;
	
	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "provider_languages", joinColumns = {
			@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "language_id", referencedColumnName = "code",nullable = false, updatable = false) })
	public Set<Language> languages;
	
	
	@ManyToMany( cascade={ CascadeType.MERGE  ,   CascadeType.REMOVE },fetch = FetchType.LAZY)
	@JoinTable(name = "provider_brokerages", joinColumns = {
			@JoinColumn(name = "prvdr_id", referencedColumnName = "prvdr_id",nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "brokerage_id", referencedColumnName = "code",nullable = false, updatable = false) })
	public Set<Brokerage> brokerages;
	

	/**
	 * 
	 */
	public Provider() {
		super();

	}

	/**
	 * @param id
	 */
	public Provider(final Integer id) {
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the hoursOfOperation
	 */
	public String getHoursOfOperation() {
		return hoursOfOperation;
	}

	/**
	 * @param hoursOfOperation the hoursOfOperation to set
	 */
	public void setHoursOfOperation(String hoursOfOperation) {
		this.hoursOfOperation = hoursOfOperation;
	}

	/**
	 * @return the ageSeen
	 */
	public String getAgeSeen() {
		return ageSeen;
	}

	/**
	 * @param ageSeen the ageSeen to set
	 */
	public void setAgeSeen(String ageSeen) {
		this.ageSeen = ageSeen;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the languages
	 */
	public Set<Language> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	/**
	 * @return the brokerages
	 */
	public Set<Brokerage> getBrokerages() {
		return brokerages;
	}

	/**
	 * @param brokerages the brokerages to set
	 */
	public void setBrokerages(Set<Brokerage> brokerages) {
		this.brokerages = brokerages;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Provider)) {
			return false;
		}
		Provider other = (Provider) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append("\n");

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for ( Field field : fields  ) {
			  if("serialVersionUID".equals(field.getName()))continue;
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append("\n");
		  }
		  result.append("}");

		  return result.toString();
	}

}
