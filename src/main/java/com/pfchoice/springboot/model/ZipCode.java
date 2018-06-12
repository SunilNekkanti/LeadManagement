package com.pfchoice.springboot.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "lu_state_zip")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"fieldHandler"})
@EqualsAndHashCode(callSuper =false,of = {"code"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ZipCode extends RecordDetails implements Serializable, FieldHandled {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "zipcode", nullable = false)
	private Integer code;

	@JsonIgnore
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statecode", referencedColumnName = "code")
	private State stateCode;

	@JsonIgnore
	private FieldHandler fieldHandler;


	/**
	 * @return the stateCode
	 */
	public State getStateCode() {
		if (fieldHandler != null) {
			return (State) fieldHandler.readObject(this, "stateCode", stateCode);
		}
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(final State stateCode) {
		if (fieldHandler != null) {
			this.stateCode = (State) fieldHandler.writeObject(this, "stateCode", this.stateCode, stateCode);
			return;
		}
		this.stateCode = stateCode;
	}

	@Override
	public void setFieldHandler(FieldHandler fieldHandler) {
		this.fieldHandler = fieldHandler;
	}

	@Override
	public FieldHandler getFieldHandler() {
		return fieldHandler;
	}

}
