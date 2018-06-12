package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author sarath
 */
@Entity(name = "file_type")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"description"})
public class FileType extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "code", nullable = false)
	private Integer code;

	@NotNull
	@Size(min = 5, max = 150, message = "The description must be between {min} and {max} characters long")
	@Column(name = "description")
	private String description;

	@NotNull(message = "Select Insurance")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ins_id", referencedColumnName = "Insurance_Id")
	private Insurance ins;

	@NotNull(message = "Select Active month indicator")
	@Column(name = "activity_Month_ind", nullable = true)
	private Character activityMonthInd;

	@Column(name = "tables_name", nullable = true)
	private String tablesName;

	@Column(name = "insurance_code", nullable = true)
	private String insuranceCode;

}
