package com.pfchoice.springboot.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.Contact;
import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.model.Event;
import com.pfchoice.springboot.model.EventAssignment;
import com.pfchoice.springboot.model.FileUpload;
import com.pfchoice.springboot.model.FileUploadContent;
import com.pfchoice.springboot.model.User;
import com.pfchoice.springboot.repositories.specifications.EventAssignmentSpecifications;
import com.pfchoice.springboot.service.EmailService;
import com.pfchoice.springboot.service.EventAssignmentService;
import com.pfchoice.springboot.service.FileUploadContentService;
import com.pfchoice.springboot.service.UserService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class EventAssignmentController {

	public static final Logger logger = LoggerFactory.getLogger(EventAssignmentController.class);

	@Autowired
	EventAssignmentService eventAssignmentService; // Service which will do all
													// data
													// retrieval/manipulation
													// work

	@Autowired
	UserService userService; // Service which will do all data//
								// retrieval/manipulation work

	@Autowired
	EmailService emailService;

	@Autowired
	FileUploadContentService fileUploadContentService;

	// -------------------Retrieve All
	// EventAssignments---------------------------------------------

	@Secured({ "ROLE_ADMIN", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/eventAssignment/", method = RequestMethod.GET)
	public ResponseEntity<Page<EventAssignment>> listAllEventAssignments(@PageableDefault(page=0 ,size=100) Pageable pageRequest,
			@RequestParam(value = "search", required = false) String search, @ModelAttribute("userId") Integer userId,
			@ModelAttribute("roleName") String roleName) {

		Specification<EventAssignment> spec = new EventAssignmentSpecifications(userId, roleName, search);
		Page<EventAssignment> eventAssignments = eventAssignmentService.findAllEventAssignmentsByPage(spec,
				pageRequest);

		if (eventAssignments.getTotalElements() == 0) {
			logger.info("no eventAssignments");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Page<EventAssignment>>(eventAssignments, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// EventAssignment------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/eventAssignment/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEventAssignment(@PathVariable("id") int id) {
		logger.info("Fetching EventAssignment with id {}", id);
		EventAssignment eventAssignment = eventAssignmentService.findById(id);
		if (eventAssignment == null) {
			logger.error("EventAssignment with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("EventAssignment with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EventAssignment>(eventAssignment, HttpStatus.OK);
	}

	// -------------------Create a
	// EventAssignment-------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR", "ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/eventAssignment/", method = RequestMethod.POST)
	public ResponseEntity<?> createEventAssignment(@RequestBody EventAssignment eventAssignment,
			UriComponentsBuilder ucBuilder, @ModelAttribute("userId") Integer userId,
			@ModelAttribute("username") String username) throws MessagingException, IOException, InterruptedException {
		logger.info("Creating EventAssignment : {}", eventAssignment);

		eventAssignmentService.saveEventAssignment(eventAssignment);

		User user = userService.findById(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
		
		Event event = eventAssignment.getEvent();
		String eventNotes = (event.getNotes() != null) ? event.getNotes() : "";

		String toEmailIds = eventAssignment.getRepresentatives().stream().map(rep -> rep.getContact().getEmail())
				.collect(Collectors.joining(";"));
		String toNamesList = eventAssignment.getRepresentatives().stream().map(rep -> rep.getUsername())
				.collect(Collectors.joining(","));
		Contact cnt = eventAssignment.getEvent().getContact();
		String address=  Stream.of(cnt.getAddress1(), cnt.getAddress2(), cnt.getCity(), cnt.getStateCode().getShortName(), cnt.getZipCode().toString())
				          .filter(s -> s != null && !s.isEmpty())
				          .collect(Collectors.joining(","));
		
		Set<FileUpload> attachments = (eventAssignment.getEvent().getAttachments() != null)?eventAssignment.getEvent().getAttachments():new HashSet<>() ;
		Set<FileUploadContent> attachmentContents = new HashSet<>();
		attachments.parallelStream().forEach(fileupload -> {
			FileUploadContent fileUploadContent = fileUploadContentService.findById(fileupload.getId());
			attachmentContents.add(fileUploadContent);
		} );	
		
		Email mail = new Email();
		mail.setEmailTo(toEmailIds);
		mail.setEmailFrom("leadmanagement@infocusonline.net");
		mail.setEmailCc(user.getContact().getEmail());
		mail.setSubject("New Event " + eventAssignment.getEvent().getEventName() + " has been Assigned to you");

		Map<String, Object> emailAttributes = new HashMap<>();
		emailAttributes.put("toNamesList", toNamesList);
		emailAttributes.put("eventName", eventAssignment.getEvent().getEventName());
		emailAttributes.put("currentUser", user.getName());
		emailAttributes.put("manager", user.getName());
		emailAttributes.put("notes", eventNotes);
		emailAttributes.put("eventStartTime", sdf.format(eventAssignment.getEvent().getStartTime().getTime()));
		emailAttributes.put("eventEndTime", sdf.format(eventAssignment.getEvent().getEndTime().getTime()));
		emailAttributes.put("currentTime", sdf.format(eventAssignment.getCreatedDate()));
		emailAttributes.put("eventStartLocalTime", sdf2.format(eventAssignment.getEvent().getStartTime().getTime()));
		emailAttributes.put("eventEndLocalTime", sdf2.format(eventAssignment.getEvent().getEndTime().getTime()));
		emailAttributes.put("currentLocalTime",sdf1.format((new Date()).getTime())); 
		emailAttributes.put("rrule", eventAssignment.getRepeatRule());
		emailAttributes.put("location", address);
		emailAttributes.put("attachments", attachmentContents);
		emailAttributes.put("uid", eventAssignment.getId()+"eventAssignment@pfchoice.com");

		mail.setModel(emailAttributes);
		String emailTemplateFileName = "event_assignment_email_template.txt";

		mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));
		emailService.sendMailWithAttachment(mail);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				ucBuilder.path("/api/eventAssignment/{id}").buildAndExpand(eventAssignment.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a EventAssignment
	// ------------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/eventAssignment/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEventAssignment(@PathVariable("id") int id,
			@RequestBody EventAssignment eventAssignment,  @ModelAttribute("userId") Integer userId,
			@ModelAttribute("username") String username) throws MessagingException, IOException, InterruptedException {
		logger.info("Updating EventAssignment with id {}", id);

		EventAssignment currentEventAssignment = eventAssignmentService.findById(id);

		if (currentEventAssignment == null) {
			logger.error("Unable to update. EventAssignment with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("Unable to upate. EventAssignment with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentEventAssignment.setRepeatRule(eventAssignment.getRepeatRule());
		currentEventAssignment.getRepresentatives().clear();
		logger.info("eventAssignment.getRepresentatives()" + eventAssignment.getRepresentatives());
		currentEventAssignment.getRepresentatives().addAll(eventAssignment.getRepresentatives());
		currentEventAssignment.setStartDate(eventAssignment.getStartDate());
		
		eventAssignmentService.updateEventAssignment(currentEventAssignment);
		
	//	eventAssignmentService.saveEventAssignment(eventAssignment);

		User user = userService.findById(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
		
		Event event = eventAssignment.getEvent();
		String eventNotes = (event.getNotes() != null) ? event.getNotes() : "";

		String toEmailIds = eventAssignment.getRepresentatives().stream().map(rep -> rep.getContact().getEmail())
				.collect(Collectors.joining(";"));
		String toNamesList = eventAssignment.getRepresentatives().stream().map(rep -> rep.getUsername())
				.collect(Collectors.joining(","));
		Contact cnt = eventAssignment.getEvent().getContact();
		String address=  Stream.of(cnt.getAddress1(), cnt.getAddress2(), cnt.getCity(), cnt.getStateCode().getShortName(), cnt.getZipCode().toString())
				          .filter(s -> s != null && !s.isEmpty())
				          .collect(Collectors.joining(","));
		Set<FileUpload> attachments = (eventAssignment.getEvent().getAttachments() != null)? eventAssignment.getEvent().getAttachments():new HashSet<>();
		Set<FileUploadContent> attachmentContents = new HashSet<>();
		
		attachments.parallelStream().forEach( fileupload -> {
			FileUploadContent fileUploadContent = fileUploadContentService.findById(fileupload.getId());
			attachmentContents.add(fileUploadContent);
		});
		Email mail = new Email();
		mail.setEmailTo(toEmailIds);
		mail.setEmailFrom("leadmanagement@infocusonline.net");
		mail.setEmailCc(user.getContact().getEmail());
		mail.setSubject("Event: " + eventAssignment.getEvent().getEventName() + " has been Assigned to you");

		Map<String, Object> emailAttributes = new HashMap<>();
		emailAttributes.put("toNamesList", toNamesList);
		emailAttributes.put("eventName", eventAssignment.getEvent().getEventName());
		emailAttributes.put("currentUser", user.getName());
		emailAttributes.put("manager", user.getName());
		emailAttributes.put("notes", eventNotes);
		
		Date startdatetime = combineDateTime(eventAssignment.getStartDate(), eventAssignment.getEvent().getStartTime());
		Date enddatetime = combineDateTime(eventAssignment.getStartDate(), eventAssignment.getEvent().getEndTime());
		
		emailAttributes.put("eventStartTime", sdf.format(startdatetime.getTime()));
		emailAttributes.put("eventEndTime", sdf.format(enddatetime.getTime()));
		emailAttributes.put("eventStartLocalTime", sdf2.format(eventAssignment.getEventDateStartTime().getTime()));
		emailAttributes.put("eventEndLocalTime", sdf2.format(eventAssignment.getEventDateEndTime().getTime()));
		emailAttributes.put("currentTime", sdf.format(eventAssignment.getCreatedDate()));
		emailAttributes.put("currentLocalTime",sdf1.format((new Date()).getTime())); 
		emailAttributes.put("rrule", eventAssignment.getRepeatRule());
		emailAttributes.put("location", address);
		emailAttributes.put("attachments", attachmentContents);
		emailAttributes.put("uid", eventAssignment.getId()+"eventAssignment@pfchoice.com");
		mail.setModel(emailAttributes);
		String emailTemplateFileName = "event_assignment_email_template.txt";

		mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));
		emailService.sendMailWithAttachment(mail);

		
		return new ResponseEntity<EventAssignment>(currentEventAssignment, HttpStatus.OK);
	}

	// ------------------- Delete a
	// EventAssignment-----------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/eventAssignment/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEventAssignment(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting EventAssignment with id {}", id);

		EventAssignment eventAssignment = eventAssignmentService.findById(id);
		if (eventAssignment == null) {
			logger.error("Unable to delete. EventAssignment with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. EventAssignment with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		eventAssignmentService.deleteEventAssignmentById(id);
		return new ResponseEntity<EventAssignment>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All
	// EventAssignments-----------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/eventAssignment/", method = RequestMethod.DELETE)
	public ResponseEntity<EventAssignment> deleteAllEventAssignments() {
		logger.info("Deleting All EventAssignments");

		eventAssignmentService.deleteAllEventAssignments();
		return new ResponseEntity<EventAssignment>(HttpStatus.NO_CONTENT);
	}
	
	
	private Date combineDateTime(Date date, Date time)
	{
		Calendar calendarA = Calendar.getInstance();
		calendarA.setTime(date);
		Calendar calendarB = Calendar.getInstance();
		calendarB.setTime(time);
	 
		calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
		calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
		calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
		calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));
	 
		Date result = calendarA.getTime();
		return result;
	}

}