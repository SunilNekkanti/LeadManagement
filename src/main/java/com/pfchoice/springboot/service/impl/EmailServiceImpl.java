package com.pfchoice.springboot.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.pfchoice.springboot.configuration.ConfigProperties;
import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.model.FileUploadContent;
import com.pfchoice.springboot.service.EmailService;

import freemarker.template.Configuration;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	Configuration fmConfiguration;
	
	@Autowired
	ConfigProperties configProperties;
	

	public static byte[] USER = "password 1234".getBytes();
	
	/**
	 * This method will send compose and send the message
	 * 
	 * @throws MessagingException
	 * @throws InterruptedException
	 */
	@Async("leadManagementExecutor")
	public void sendMail(final Email mail) throws MessagingException, InterruptedException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		String[] toEmailList = mail.getEmailTo().split(";");
		helper.setTo(toEmailList);
		helper.setFrom("leadmanagement@infocusonline.net");
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setCc(mail.getEmailCc());
		Thread.sleep(3000);
		mailSender.send(message);
		LOGGER.info("an email sent from the server");
	}

	/**
	 * This method will send compose and send the message
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	@Async("leadManagementExecutor")
	public void sendMailWithAttachment(final Email mail) throws MessagingException, IOException, InterruptedException {
		Thread.sleep(3000);
		MimeMessage message = mailSender.createMimeMessage();
		message.addHeaderLine("charset=UTF-8");
		message.addHeaderLine("component=VEVENT");
		message.addHeaderLine("method=REQUEST");

		MimeMessageHelper helper = new MimeMessageHelper(message);
		 String[] toEmailList = mail.getEmailTo().split(";");
		helper.setTo(toEmailList);
		helper.setFrom("leadmanagement@infocusonline.net");
		helper.setSubject(mail.getSubject());
		helper.setCc(mail.getEmailCc());
		helper.setText(mail.getBody(), true);
		
		Map<String, Object> emailAttributes =  mail.getModel();
		 String startDateTime = (emailAttributes.get("appointmentStartTime") ==null)? emailAttributes.get("eventStartTime").toString():emailAttributes.get("appointmentStartTime").toString();
		 String endDateTime = (emailAttributes.get("appointmentEndTime") ==null)? emailAttributes.get("eventEndTime").toString():emailAttributes.get("appointmentEndTime").toString();
         String location =   (emailAttributes.get("location") ==null)? "":emailAttributes.get("location").toString();
         String currentTime = (emailAttributes.get("currentTime") ==null)? "":emailAttributes.get("currentTime").toString();
         String firstName  = (emailAttributes.get("firstName") ==null)? "":emailAttributes.get("firstName").toString();
         String lastName  = (emailAttributes.get("lastName") ==null)? "":emailAttributes.get("lastName").toString();
         String leadName = lastName+","+firstName;
         String eventName  = ("".equals(leadName))? (emailAttributes.get("eventName") ==null)? "":emailAttributes.get("eventName").toString():leadName;
         String owner  = (emailAttributes.get("attachmentKey") ==null)? "":emailAttributes.get("attachmentKey").toString();
         
		String rrule = (emailAttributes.get("rrule") != null && !"".equals(emailAttributes.get("rrule")))
				? "RRULE:" + emailAttributes.get("rrule").toString() + "\n" : "";
		Set<FileUploadContent> attachments =  (Set<FileUploadContent>) emailAttributes.get("attachments");
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
		            + "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;PARTSTAT=ACCEPTED:MAILTO:"+configProperties.getCoordinatorEmail()+"\n"
		            + "ORGANIZER:MAILTO:"+configProperties.getCoordinatorEmail()+"\n"
		            + "DTSTART;TZID=America/New_York:"+startDateTime+"\n"
		            + "DTEND;TZID=America/New_York:"+endDateTime+"\n"
		            + "LOCATION:"+location+"\n"
		            + "TRANSP:OPAQUE\n"
		            + "SEQUENCE:0\n"
		            + "UID:ABCDXXXXXEEEEEEEEEEEEEEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDFFFFFFFFFFFFFFFFFFFFFFFF\n"
		            + rrule
		            + "DTSTAMP:"+currentTime+"\n"
		            + "CATEGORIES:Meeting\n"
		            + "DESCRIPTION:"+eventName +"\n"
		            + "SUMMARY:"+eventName+"\n"
		            + "PRIORITY:5\n"
		            + "CLASS:PUBLIC\n"
		            + "STATUS:CONFIRMED\n"
		            + "BEGIN:VALARM\n"
		            + "TRIGGER:PT1440M\n"
		            + "ACTION:DISPLAY\n"
		            + "DESCRIPTION:Reminder\n"
		            + "END:VALARM\n"
		            + "END:VEVENT\n"
		            + "END:VCALENDAR");
		    
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			 messageBodyPart.setContent(mail.getBody(), "text/html");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			if( attachments != null && attachments.size()> 0) {
				try {
					for( FileUploadContent fileUpload: attachments) { 
						BodyPart attachmentBodyPart = new MimeBodyPart();
						attachmentBodyPart.setContent(mail.getBody(), "text/html");
						
						Document document = new Document(PageSize.A4);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ByteArrayOutputStream singedbaos = new ByteArrayOutputStream();
						PdfReader reader;
					    try {
					    	PdfWriter.getInstance(document, baos);
					    	document.open();
					    	if(fileUpload.getFileName().contains(".jpg") || fileUpload.getFileName().contains(".jpeg") || fileUpload.getFileName().contains(".png")){
					    		 Image img =   Image.getInstance(fileUpload.getData());
					    		 img.setAlignment(Image.ALIGN_LEFT);
					    		 img.setAbsolutePosition(0f, 0f);
					    		 img.scalePercent(40, 40);

							     document.add(img);
					    	}
					       
					    } catch (Exception e) {
					    	LOGGER.warning(e.getMessage());
					    }
					    document.close();
					    if(baos.size() > 1){
					    	reader = new PdfReader(baos.toByteArray());
					    }else{
					    	reader = new PdfReader(fileUpload.getData());
					    }
					     
				        PdfStamper stamper = new PdfStamper(reader, singedbaos);
				        stamper.setEncryption(USER, owner.getBytes(),
				            PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
				        stamper.close();
					    
					    DataSource source = new ByteArrayDataSource(singedbaos.toByteArray(),"application/pdf");
						attachmentBodyPart.setDataHandler(new DataHandler(source));
						attachmentBodyPart.setFileName("LeadConsentForm.pdf");
						multipart.addBodyPart(attachmentBodyPart);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
		
			}
			
			// file attachment
			BodyPart calendarBodyPart = new MimeBodyPart();
			calendarBodyPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
            calendarBodyPart.setContent(buffer.toString(), "text/calendar;method=CANCEL");
		        
		    multipart.addBodyPart(calendarBodyPart);

		    message.setContent(multipart);
		    
		mailSender.send(message);
		LOGGER.info("an email with calendar sent from the server");
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