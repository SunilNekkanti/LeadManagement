package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 *
 * @author sarath
 */
@Entity
@Table(name = "Lead_membership_provider")
public class LeadMembershipProvider extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_mbr_prvdr_id", nullable = false)
	private Integer id;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prvdr_id", nullable = false, referencedColumnName = "prvdr_id")
	private Provider prvdr;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "lead_mbr_id", nullable = false, referencedColumnName = "lead_mbr_id")
	private LeadMembership leadMbr;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "eff_start_date")
	private Date effStartDate;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "eff_end_date")
	private Date effEndDate;
	
	
	@Column(name = "file_id")
	private Integer fileId;

	/**
	 * 
	 */
	public LeadMembershipProvider() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadMembershipProvider(final Integer id) {
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
	 * @return the mbrId
	 */
	public LeadMembership getLeadMbr() {
		return leadMbr;
	}

	/**
	 * @param mbr
	 *            the mbr to set
	 */
	public void setLeadMbr(final LeadMembership leadMbr) {
		this.leadMbr = leadMbr;
	}

	/**
	 * @return the prvdr
	 */
	public Provider getPrvdr() {
		return prvdr;
	}

	/**
	 * @param prvdr
	 *            the prvdr to set
	 */
	public void setPrvdr(final Provider prvdr) {
		this.prvdr = prvdr;
	}

	/**
	 * @return the effStartDate
	 */
	public Date getEffStartDate() {
		return effStartDate;
	}

	/**
	 * @param effStartDate
	 *            the effStartDate to set
	 */
	public void setEffStartDate(final Date effStartDate) {
		this.effStartDate = effStartDate;
	}

	/**
	 * @return the effEndDate
	 */
	public Date getEffEndDate() {
		return effEndDate;
	}

	/**
	 * @param effEndDate
	 *            the effEndDate to set
	 */
	public void setEffEndDate(final Date effEndDate) {
		this.effEndDate = effEndDate;
	}
	
	/**
	 * @return
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LeadMembershipProvider)) {
			return false;
		}
		LeadMembershipProvider other = (LeadMembershipProvider) object;
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
