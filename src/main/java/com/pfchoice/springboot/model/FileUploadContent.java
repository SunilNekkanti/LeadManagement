package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity
@Table(name = "file_upload")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"fileName"})

public class FileUploadContent extends RecordDetails implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "file_upload_Id", nullable = false)
	protected Integer id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "file_data", nullable = false, columnDefinition = "mediumblob")
	private byte[] data;

}
