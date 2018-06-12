package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Map;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Sarath Gandluri
 */
@Entity
@Table(name = "emails")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"filesUpload"})
@EqualsAndHashCode(callSuper =false,of = {"emailTo", "subject"})
public class Email extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "email_id", nullable = false)
	private Integer id;

	@Column(name = "email_to")
	private String emailTo;

	@Column(name = "email_from")
	private String emailFrom;

	@Column(name = "email_cc")
	private String emailCc;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body", length = 65535, columnDefinition = "TEXT")
	private String body;

	@Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "emails_files_upload", joinColumns = {
			@JoinColumn(name = "email_id", referencedColumnName = "email_id") }, inverseJoinColumns = {
					@JoinColumn(name = "files_upload_id", referencedColumnName = "file_upload_id") })
	private Set<FileUpload> filesUpload;

	@Column(name = "template_filename", nullable = false)
	private String templateFile;

	@Transient
	private Map<String, Object> model;

}
