package com.pfchoice.springboot.service;


import java.io.IOException;

import javax.mail.MessagingException;

import com.pfchoice.springboot.model.Email;

public interface EmailService  {
	
	 void sendMail(Email eParams) throws MessagingException ; 
	
	 void sendMailWithAttachment(Email eParams) throws MessagingException, IOException ;


}