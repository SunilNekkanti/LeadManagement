package com.pfchoice.springboot.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
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
@Scope(value="session")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginForm  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String roleName;
	private Integer roleId;
	private Integer userId;
	private Integer insuranceId;
	

}
