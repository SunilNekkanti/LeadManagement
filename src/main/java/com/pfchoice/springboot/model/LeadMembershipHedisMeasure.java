package com.pfchoice.springboot.model;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 *
 * @author sarath
 */
@Entity(name = "lead_membership_hedis_measure")
public class LeadMembershipHedisMeasure extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lead_mbr_hedis_msr_Id", nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_mbr_id", nullable = false, referencedColumnName = "lead_mbr_id")
	private LeadMembership leadMbr;

	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hedis_msr_rule_id", nullable = false, referencedColumnName = "hedis_msr_rule_id")
	private HedisMeasureRule hedisMeasureRule;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "due_date")
	private Date dueDate;

	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_service")
	private Date dos;

	
	@Column(name = "follow_up_ind")
	private Character followUpInd;

	/**
	 * 
	 */
	public LeadMembershipHedisMeasure() {
		super();
	}

	/**
	 * @param id
	 */
	public LeadMembershipHedisMeasure(final Integer id) {
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
	public void setMbr(final LeadMembership leadMbr) {
		this.leadMbr = leadMbr;
	}

	/**
	 * @return the hedisMeasureRule
	 */
	public HedisMeasureRule getHedisMeasureRule() {
		return hedisMeasureRule;
	}

	/**
	 * @param hedisMeasureRule
	 *            the hedisMeasureRule to set
	 */
	public void setHedisMeasureRule(final HedisMeasureRule hedisMeasureRule) {
		this.hedisMeasureRule = hedisMeasureRule;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the dos
	 */
	public Date getDos() {
		return dos;
	}

	/**
	 * @param dos
	 *            the dos to set
	 */
	public void setDos(final Date dos) {
		this.dos = dos;
	}

	/**
	 * @return the followUpInd
	 */
	public Character getFollowUpInd() {
		return followUpInd;
	}

	/**
	 * @param followUpInd
	 *            the followUpInd to set
	 */
	public void setFollowUpInd(final Character followUpInd) {
		this.followUpInd = followUpInd;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LeadMembershipHedisMeasure)) {
			return false;
		}
		LeadMembershipHedisMeasure other = (LeadMembershipHedisMeasure) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.MemberhsipHedisMeasure[ id=" + id + " ]";
	}

}
