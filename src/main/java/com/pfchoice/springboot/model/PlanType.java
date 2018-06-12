package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "lu_plan_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,of = {"code","description"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PlanType extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "plan_type_id", nullable = false)
	private Integer id;

	@NotNull
	@Size(min = 2, max = 5, message = "The code must be between {min} and {max} characters long")
	@Column(name = "code")
	private String code;

	@NotNull
	@Size(min = 5, max = 200, message = "The description must be between {min} and {max} characters long")
	@Column(name = "description")
	private String description;

}
