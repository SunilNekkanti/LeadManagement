package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
@Table(name = "lu_state")

@NoArgsConstructor
@ToString(exclude={"fieldHandler","zipCodes"})
@EqualsAndHashCode(callSuper =false,exclude = {"fieldHandler","zipCodes"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
public class State extends RecordDetails implements Serializable, FieldHandled {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "code", nullable = false)
	@Getter
	@Setter
	private Integer code;

	@Column(name = "description")
	@Getter
	@Setter
	private String description;

	@Column(name = "shot_name")
	@Getter
	@Setter
	private String shortName;

	@Fetch(FetchMode.SELECT)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@OneToMany(mappedBy = "stateCode", fetch = FetchType.LAZY)
	private Set<ZipCode> zipCodes;

	@JsonIgnore
	private FieldHandler fieldHandler;

 

	/**
	 * @return the zipCode
	 */
	@SuppressWarnings("unchecked")
	public Set<ZipCode> getZipCodes() {
		if (fieldHandler != null) {
			return (Set<ZipCode>) fieldHandler.readObject(this, "zipCodes", zipCodes);
		} else {
			return zipCodes;
		}

	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	@SuppressWarnings("unchecked")
	public void setZipCodes(final Set<ZipCode> zipCodes) {
		if (fieldHandler != null) {
			this.zipCodes = (Set<ZipCode>) fieldHandler.writeObject(this, "zipCodes", this.zipCodes, zipCodes);
			return;
		}
		this.zipCodes = zipCodes;
	}

	public void setFieldHandler(FieldHandler fieldHandler) {
		this.fieldHandler = fieldHandler;
	}

	public FieldHandler getFieldHandler() {
		return fieldHandler;
	}

}
