package com.pfchoice.springboot.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
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
	 * 
	 * @throws MessagingException
	 * @throws InterruptedException
	 */
	public void sendMail(Email mail) throws MessagingException, InterruptedException {
		Thread.sleep(10000);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		String[] toEmailList = mail.getEmailTo().split(";");
		helper.setTo(toEmailList);
		helper.setFrom(mail.getEmailFrom());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setCc(mail.getEmailCc());
		mailSender.send(message);
		LOGGER.info("an email sent from the server");
	}

	/**
	 * This method will send compose and send the message
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendMailWithAttachment(Email mail) throws MessagingException, IOException, InterruptedException {

	//	MimeMessage message = mailSender.createMimeMessage();
	//	message.addHeaderLine("charset=UTF-8");
	//	message.addHeaderLine("component=VEVENT");
	//	message.addHeaderLine("method=REQUEST");
//
		//MimeMessageHelper helper = new MimeMessageHelper(message);
		/*String[] toEmailList = mail.getEmailTo().split(";");
		helper.setTo(toEmailList);
		helper.setFrom(mail.getEmailFrom());
		helper.setSubject(mail.getSubject());
		helper.setCc(mail.getEmailCc());
		helper.setText(mail.getBody(), true);

		Map<String, Object> emailAttributes = mail.getModel();
		String rrule = (emailAttributes.get("rrule") != null)
				? "RRULE:" + emailAttributes.get("rrule").toString() + "\n" : "";
		System.out.println("rule" + rrule);
		*/
		
		 StringBuffer sb = new StringBuffer();

		    StringBuffer buffer = sb.append(
		            "BEGIN:VCALENDAR\n"
		            + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
		            + "VERSION:2.0\n"
		            + "METHOD:REQUEST\n"
		            + "BEGIN:VTIMEZONE\n"
		            + "TZID:America/New_York\n"
		            + "X-LIC-LOCATION:America/New_York\n"
		            + "BEGIN:STANDARD\n"
		            + "DTSTART:20071104T020000Z\n"
		            + "TZOFFSETFROM:-0400\n"
		            + "TZOFFSETTO:-0500\n"
		            + "TZNAME:EST\n"
		            + "END:STANDARD\n"
		            + "BEGIN:DAYLIGHT\n"
		            + "DTSTART:20070311T020000Z\n"
		            + "TZOFFSETFROM:-0500\n"
		            + "TZOFFSETTO:-0400\n"
		            + "TZNAME:EDT\n"
		            + "END:DAYLIGHT\n"
		            + "END:VTIMEZONE\n"
		            + "BEGIN:VEVENT\n"
		            + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:skumar@pfchoice.com\n"
		            + "ORGANIZER:MAILTO:skumar@pfchoice.com\n"
		            + "DTSTART;TZID=America/New_York:20170831T100000Z\n"
		            + "DTEND;TZID=America/New_York:20170831T110000Z\n"
		            + "LOCATION:Conference room\n"
		            + "TRANSP:OPAQUE\n"
		            + "SEQUENCE:0\n"
		            + "UID:ABCDXXXXXEEEEEEEEEEEEEEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDFFFFFFFFFFFFFFFFFFFFFFFF\n"
		            + "DTSTAMP:20170831T120102Z\n"
		            + "CATEGORIES:Meeting\n"
		            + "DESCRIPTION:testingemail calendar\n"
		            + "SUMMARY:testing email calendar\n"
		            + "PRIORITY:5\n"
		            + "CLASS:PUBLIC\n"
		            + "BEGIN:VALARM\n"
		            + "TRIGGER:PT1440M\n"
		            + "ACTION:DISPLAY\n"
		            + "DESCRIPTION:Reminder\n"
		            + "END:VALARM\n"
		            + "END:VEVENT\n"
		            + "END:VCALENDAR");

		    MimeMessage message = mailSender.createMimeMessage();
		    message.addHeaderLine("charset=UTF-8");
		    message.addHeaderLine("component=VEVENT");
		    message.addHeaderLine("method=REQUEST");

		    message.setFrom("skumar@pfchoice.com");
		    message.addRecipient(Message.RecipientType.TO,  new InternetAddress("skumar@pfchoice.com"));
		    message.setSubject("TTTTTesting calendar");

		    BodyPart messageBodyPart = new MimeBodyPart();

		    messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
		    messageBodyPart.setHeader("Content-ID", "calendar_message");
		    messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));

		    Multipart multipart = new MimeMultipart();

		    multipart.addBodyPart(messageBodyPart);

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