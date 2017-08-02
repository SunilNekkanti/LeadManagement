package com.pfchoice.springboot.service.impl;

import java.io.IOException;
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

import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	@Autowired
	private JavaMailSender mailSender;
 

	/**
	 * This method will send compose and send the message
	 * @throws MessagingException 
	 */
	public void sendMail(Email eParams) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getEmailTo());
		helper.setFrom(eParams.getEmailFrom());
		helper.setSubject("email test");
		helper.setText(eParams.getBody(), true);
		helper.setCc(eParams.getEmailCc());
		mailSender.send(message);
		LOGGER.info("an email sent from the server");
	}

	/**
	 * This method will send compose and send the message
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public void sendMailWithAttachment(Email eParams) throws MessagingException, IOException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getEmailTo());
		helper.setFrom(eParams.getEmailFrom());
		helper.setSubject("email test");
		helper.setCc(eParams.getEmailCc());

		StringBuffer sb = new StringBuffer();

        StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n" +
                "ORGANIZER:MAILTO:xx@xx.com\n" +
                "DTSTART:20161208T053000Z\n" +
                "DTEND:20161208T060000Z\n" +
                "LOCATION:Conferenceroom\n" +
                "TRANSP:OPAQUE\n" +
                "SEQUENCE:0\n" +
                "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                "RRULE:FREQ=WEEKLY;INTERVAL=2;COUNT=8;WKST=SU;BYDAY=TU,TH\n"+
                " 000004377FE5C37984842BF9440448399EB02\n" +
                "DTSTAMP:20051206T120102Z\n" +
                "CATEGORIES:Meeting\n" +
                "DESCRIPTION:This the description of the meeting.\n\n" +
                "SUMMARY:Test meeting request\n" +
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

			// Now set the actual message
			messageBodyPart.setText(eParams.getBody());

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


}