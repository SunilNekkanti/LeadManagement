package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "email_templates")
public class EmailTemplate extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "email_temp_id", nullable = false)
	private Integer id;

	
	@Column(name = "description")
	private String description;

	
	@Column(name = "template", length = 65535, columnDefinition = "TEXT")
	private String template;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "emailTemplate")
	private List<EmailTemplatePlaceholder> emailTemplatePlacholderList;

	/**
	 * 
	 */
	public EmailTemplate() {
		super();
	}

	/**
	 * @param id
	 */
	public EmailTemplate(final Integer id) {
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the emailTemplatePlacholderList
	 */
	public List<EmailTemplatePlaceholder> getEmailTemplatePlacholderList() {
		return emailTemplatePlacholderList;
	}

	/**
	 * @param emailTemplatePlacholderList
	 *            the emailTemplatePlacholderList to set
	 */
	public void setEmailTemplatePlacholderList(List<EmailTemplatePlaceholder> emailTemplatePlacholderList) {
		this.emailTemplatePlacholderList = emailTemplatePlacholderList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EmailTemplate)) {
			return false;
		}
		EmailTemplate other = (EmailTemplate) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.pfchoice.core.entity.EmailTemplates[ id=" + id + " ]";
	}

}
