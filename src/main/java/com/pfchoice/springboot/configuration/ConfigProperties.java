package com.pfchoice.springboot.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("envProperties")
public class ConfigProperties {

	@NotBlank
	private String coordinatorEmail;

	/**
	 * @return the coordinatorEmail
	 */
	public String getCoordinatorEmail() {
		return coordinatorEmail;
	}

	/**
	 * @param coordinatorEmail the coordinatorEmail to set
	 */
	public void setCoordinatorEmail(String coordinatorEmail) {
		this.coordinatorEmail = coordinatorEmail;
	}
 
 

}