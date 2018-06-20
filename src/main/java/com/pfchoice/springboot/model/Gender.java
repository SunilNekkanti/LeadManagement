package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "lu_gender")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,exclude = {"id"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Gender extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "gender_id", nullable = false)
	private Byte id;

	@Column(name = "code")
	private char code;

	@Column(name = "description")
	private String description;

}
