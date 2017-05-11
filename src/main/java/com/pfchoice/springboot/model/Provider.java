package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 *
 * @author Mohanasundharam
 */
@Entity
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

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prvdr")
	private Set<ReferenceContact> refContacts = new HashSet<>();

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prvdr")
	private Set<ReferenceContract> refContracts = new HashSet<>();

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
	 * @return the refContact
	 */
	public Set<ReferenceContact> getRefContacts() {
		return refContacts;
	}

	/**
	 * @param refContact
	 *            the refContact to set
	 */
	public void setRefContacts(Set<ReferenceContact> refContacts) {
		this.refContacts = refContacts;
	}

	/**
	 * @return the refContracts
	 */
	public Set<ReferenceContract> getRefContracts() {
		return refContracts;
	}

	/**
	 * @param refContracts
	 *            the refContracts to set
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
