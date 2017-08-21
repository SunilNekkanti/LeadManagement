package com.pfchoice.springboot.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.service.EmailService;

import freemarker.template.Configuration;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	@Autowired
	private JavaMailSender mailSender;
 

	@Autowired
    Configuration fmConfiguration;
	
	/**
	 * This method will send compose and send the message
	 * @throws MessagingException 
	 * @throws InterruptedException 
	 */
	public void sendMail(Email mail) throws MessagingException, InterruptedException {
		 Thread.sleep(10000);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(mail.getEmailTo());
		helper.setFrom(mail.getEmailFrom());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setCc(mail.getEmailCc());
		mailSender.send(message);
		LOGGER.info("an email sent from the server");
	}

	/**
	 * This method will send compose and send the message
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public void sendMailWithAttachment(Email mail) throws MessagingException, IOException, InterruptedException {

		MimeMessage message = mailSender.createMimeMessage();
		message.addHeaderLine("charset=UTF-8");
	    message.addHeaderLine("component=VEVENT");
	    message.addHeaderLine("method=REQUEST");
	    
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(mail.getEmailTo());
		helper.setFrom(mail.getEmailFrom());
		helper.setSubject(mail.getSubject());
		helper.setCc(mail.getEmailCc());
		helper.setText(mail.getBody(), true);
		
		Map<String, Object> emailAttributes =  mail.getModel();
		String rrule = (emailAttributes.get("rrule") != null) ? "RRULE:"+emailAttributes.get("rrule").toString()+"\n":"";
		StringBuffer sb = new StringBuffer();
		 String startDateTime = (emailAttributes.get("appointmentStartTime") ==null)? emailAttributes.get("eventStartTime").toString():emailAttributes.get("appointmentStartTime") .toString();
		 String endDateTime = (emailAttributes.get("appointmentEndTime") ==null)? emailAttributes.get("eventEndTime").toString():emailAttributes.get("appointmentEndTime") .toString();
        StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:"+mail.getEmailTo()+"\n" +
                "ORGANIZER:MAILTO:"+mail.getEmailCc()+"\n" +
                "DTSTART:"+startDateTime+"\n" +
                "DTEND:"+endDateTime+"\n" +
                "LOCATION:"+emailAttributes.get("location").toString()+"\n" +
                "TRANSP:OPAQUE\n" +
                "SEQUENCE:0\n" +
                "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                 rrule+ 
                " 000004377FE5C37984842BF9440448399EB02\n" +
                "DTSTAMP:"+emailAttributes.get("currentTime").toString() +"\n" +
                "CATEGORIES:Meeting\n" +
                "DESCRIPTION:"+mail.getSubject()+"\n\n" +
                "SUMMARY:"+mail.getSubject()+"\n" +
                "PRIORITY:5\n" +
                "CLASS:PUBLIC\n" +
                "BEGIN:VALARM\n" +
                "TRIGGER:PT1440M\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:Reminder\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
        
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mail.getBody(), "text/html");

			// Now set the actual message
			//messageBodyPart.setText);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

		/*	DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
*/
			   // Fill the message
            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

            multipart.addBodyPart(messageBodyPart);
			// Send the complete message parts
			message.setContent(multipart);

			mailSender.send(message);
	}

	public String geContentFromTemplate(Object model, String emailTemplateFile) {
        StringBuffer content = new StringBuffer();
 
        try {
            content.append(FreeMarkerTemplateUtils
                .processTemplateIntoString(fmConfiguration.getTemplate(emailTemplateFile), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
	public String geContentFromTemplate(Map<String, Object> model, String emailTemplateFile) {
        StringBuffer content = new StringBuffer();
 
        try {
            content.append(FreeMarkerTemplateUtils
                .processTemplateIntoString(fmConfiguration.getTemplate(emailTemplateFile), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
}