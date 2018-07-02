package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "lu_state")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper =false,exclude = { "code"})
public class State extends RecordDetails implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "code", nullable = false)
	private Integer code;

	@Column(name = "description")
	private String description;

	@Column(name = "shot_name")
	private String shortName;

}
