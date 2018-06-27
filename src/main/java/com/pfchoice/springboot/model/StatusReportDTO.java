package com.pfchoice.springboot.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
 * @author sarath
 *
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper =false,exclude = {"id"})
public class StatusReportDTO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String lastName;

	private String firstName;

	private String initialInsurance;

	private String status;

	private String event;
	
	private String planType;

    private Integer count;
	
	private String userName;
	
	private String notes;
	
	
	public StatusReportDTO(String lastName, String firstName, String initialInsurance, String planType, String status,
			String event, Integer count, String userName, String notes) {
		super();
		this.lastName = lastName;
		this.planType = planType;
		this.initialInsurance = initialInsurance;
		this.firstName = firstName;
		this.status = status;
		this.event = event;
		this.count = count;
		this.userName = userName;
		this.notes = notes;
	}

}
