package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;



/**
 *
 * @author Mohanasundharam
 */
@Entity
@Table(name = "lead_membership_problems")
public class LeadMembershipProblem extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_mbr_pbm_id", nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_mbr_id", nullable = false, referencedColumnName = "lead_mbr_id")
	private LeadMembership leadMbr;

	
	@OneToOne(fetch = FetchType.EAGER)
	@NotNull(message = "Select Problem")
	@JoinColumn(name = "pbm_id", nullable = false, referencedColumnName = "pbm_id")
	private Problem pbm;

	
	@NotNull(message = "Diagnose date must not be null")
	@Column(name = "start_date", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDate;

	
	@Column(name = "resolved_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date resolvedDate;

	
	@Column(name = "file_id")
	private Integer fileId;

	/**
	 * 
	 */
	public LeadMembershipProblem() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadMembershipProblem(final Integer id) {
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
	 * @return the leadMbr
	 */
	public LeadMembership getLeadMbr() {
		return leadMbr;
	}

	/**
	 * @param mbr
	 *            the leadMbr to set
	 */
	public void setLeadMbr(LeadMembership leadMbr) {
		this.leadMbr = leadMbr;
	}

	/**
	 * @return the pbm
	 */
	public Problem getPbm() {
		return pbm;
	}

	/**
	 * @param pbm
	 *            the pbm to set
	 */
	public void setPbm(Problem pbm) {
		this.pbm = pbm;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the resolvedDate
	 */
	public Date getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate
	 *            the resolvedDate to set
	 */
	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
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
		if (!(object instanceof LeadMembershipProblem)) {
			return false;
		}
		LeadMembershipProblem other = (LeadMembershipProblem) object;
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
