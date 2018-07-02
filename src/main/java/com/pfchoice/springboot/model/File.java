package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity(name = "file")

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"fileName"})
public class File extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "file_id", nullable = false)
	private Integer id;

	@Column(name = "file_name")
	private String fileName;

	@ManyToOne
	@JoinColumn(name = "file_type_code", referencedColumnName = "code")
	private FileType fileType;

}
