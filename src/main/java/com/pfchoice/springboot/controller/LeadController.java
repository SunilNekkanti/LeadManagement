package com.pfchoice.springboot.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

import com.pfchoice.springboot.configuration.ConfigProperties;
import com.pfchoice.springboot.model.AgentLeadAppointment;
import com.pfchoice.springboot.model.Contact;
import com.pfchoice.springboot.model.CurrentUser;
import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.model.FileUpload;
import com.pfchoice.springboot.model.FileUploadContent;
import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.model.LeadNotes;
import com.pfchoice.springboot.model.User;
import com.pfchoice.springboot.repositories.specifications.LeadSpecifications;
import com.pfchoice.springboot.service.CurrentUserService;
import com.pfchoice.springboot.service.EmailService;
import com.pfchoice.springboot.service.FileUploadContentService;
import com.pfchoice.springboot.service.LeadMembershipService;
import com.pfchoice.springboot.service.UserService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class LeadController {

	public static final Logger logger = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	LeadMembershipService leadService; // Service which will do all data
										// retrieval/manipulation work

	@Autowired
	UserService userService; // Service which will do all data
								// retrieval/manipulation work
	@Autowired
	CurrentUserService currentUserService; // Service which will do all data
	// retrieval/manipulation work

	@Autowired
	EmailService emailService;
	
	@Autowired
	FileUploadContentService fileUploadContentService;
	
	@Autowired
	ConfigProperties configProp;

	// -------------------Retrieve All
	// LeadMemberships---------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/lead/", method = RequestMethod.GET)
	public ResponseEntity<Page<LeadMembership>> listAllLeadMemberships(
			@PageableDefault(page=0 ,size=100) Pageable pageRequest,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "selectedGender", required = false) Integer selectedGender,
			@RequestParam(value = "phoneNo", required = false) String phoneNo,
			@RequestParam(value = "selectedLang", required = false) Integer selectedLang,
			@RequestParam(value = "selectedStatus", required = false) Integer selectedStatus,
			@RequestParam(value = "selectedStDetails", required = false) Integer selectedStDetails,
			@ModelAttribute("userId") Integer userId,
			@ModelAttribute("roleName") String roleName, @ModelAttribute("username") String username)
			throws MessagingException, IOException {

		Specification<LeadMembership> spec = new LeadSpecifications(userId, username, roleName, firstName, lastName,
			 selectedGender, phoneNo, selectedLang, selectedStatus, selectedStDetails, search);
		Page<LeadMembership> leads = leadService.findAllLeadMembershipsByPage(spec, pageRequest);
		if (leads.getTotalElements() == 0) {
			logger.info("no leads");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Page<LeadMembership>>(leads, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// LeadMembership------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLeadMembership(@PathVariable("id") int id) {
		logger.info("Fetching LeadMembership with id {}", id);
		LeadMembership lead = leadService.findById(id);
		if (lead == null) {
			logger.error("LeadMembership with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("LeadMembership with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeadMembership>(lead, HttpStatus.OK);
	}

	// -------------------Create a
	// LeadMembership-------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/lead/", method = RequestMethod.POST)
	public ResponseEntity<?> createLeadMembership(@RequestBody LeadMembership lead, UriComponentsBuilder ucBuilder,
			@ModelAttribute("userId") Integer userId, 
			@ModelAttribute("roleName") String roleName,@ModelAttribute("username") String username) throws Exception {
		logger.info("Creating LeadMembership : {}", lead);

		final String firstName = lead.getFirstName();
		final String lastName = lead.getLastName();
		final String address1 = lead.getContact().getAddress1();
		final String phoneNumber = lead.getContact().getHomePhone();

		if (leadService.isLeadMembershipExists(firstName, lastName, address1, phoneNumber)) {
			logger.error("Unable to create. A LeadMembership with name {} already exist",
					lead.getFirstName() + lead.getLastName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A LeadMembership with name "
					+ lead.getFirstName() + " " + lead.getLastName() + " already exist."), HttpStatus.CONFLICT);
		}
		 if(lead.getAgentLeadAppointmentList() != null && lead.getAgentLeadAppointmentList().size()>0) {
			 lead.getAgentLeadAppointmentList().forEach( ala -> ala.setLead(lead));
		 }
		StringBuffer emailBody = new StringBuffer();
		User user = userService.findById(userId);
		List<LeadNotes> leadNotes = lead.getLeadNotes();
		if (leadNotes != null) {
			leadNotes.forEach(ln -> {
				ln.setUser(user);
				ln.setLead(lead);
				emailBody.append(ln.getNotes());
			});
		} else {
			emailBody.append("");
		}
        
		lead.setLeadNotes(leadNotes);
		leadService.saveLeadMembership(lead);

		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String currentLocalTime = sdf1.format((new Date()).getTime());
		
		/*
		 * String toEmailIds =
		 * lead.getEvent().getRepresentatives().stream().map(rep ->
		 * rep.getEmail()) .collect(Collectors.joining(","));
		 */
		Email mail = new Email();
		mail.setEmailTo(configProp.getCoordinatorEmail());  
		mail.setEmailFrom("leadmanagement@infocusonline.net");
		mail.setEmailCc("leadmanagement@infocusonline.net");
		mail.setSubject("New lead : "+lead.getLastName()+","+lead.getFirstName()+" has been created:");

		String careCoordinator = configProp.getCoordinatorName();
		
		Map<String, Object> emailAttributes = new HashMap<>();
		emailAttributes.put("currentUser", user.getName());
		emailAttributes.put("currentUserRole", roleName);
		emailAttributes.put("currentLocalTime", currentLocalTime);
		emailAttributes.put("careCoordinator", careCoordinator);
		emailAttributes.put("firstName", lead.getFirstName());
		emailAttributes.put("lastName", lead.getLastName());
		
		String emailTemplateFileName = "lead_create_email_template_" + roleName + ".txt";
		mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));

		emailService.sendMail(mail);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/lead/{id}").buildAndExpand(lead.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a LeadMembership
	// ------------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLeadMembership(@PathVariable("id") int id, @RequestBody LeadMembership lead,
			@ModelAttribute("userId") Integer userId, @ModelAttribute("roleName") String roleName,
			@ModelAttribute("username") String username) throws MessagingException, InterruptedException, IOException {
		logger.info("Updating LeadMembership with id {}", id);
		
		List<AgentLeadAppointment> finalAgentLeadAppointList = new ArrayList<>();
		List<AgentLeadAppointment> agntLeadAppointList = new ArrayList<>();
		String currentUserLeadNotes = "";
		
		LeadMembership currentLeadMembership = leadService.findById(id);

		CurrentUser loginUser = currentUserService.findById(userId);
		if (currentLeadMembership == null) {
			logger.error("Unable to update. LeadMembership with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("Unable to upate. LeadMembership with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		currentLeadMembership.setFirstName(lead.getFirstName());
		currentLeadMembership.setLastName(lead.getLastName());
		currentLeadMembership.setDob(lead.getDob());
		currentLeadMembership.setEthinicCode(lead.getEthinicCode());
		currentLeadMembership.setPlanType(lead.getPlanType());
		currentLeadMembership.setInitialInsurance(lead.getInitialInsurance());
		currentLeadMembership.setGender(lead.getGender());
		currentLeadMembership.setStatus(lead.getStatus());
		currentLeadMembership.setLanguage(lead.getLanguage());
		currentLeadMembership.setBestTimeToCall(lead.getBestTimeToCall());
		currentLeadMembership.setStatus(lead.getStatus());
		currentLeadMembership.setStatusDetail(lead.getStatusDetail());
		currentLeadMembership.setFileUpload(lead.getFileUpload());
		currentLeadMembership.setConsentFormSigned(lead.getConsentFormSigned());
		currentLeadMembership.setContact(lead.getContact());
		currentLeadMembership.setEvent(lead.getEvent());

		List<LeadNotes> leadNotes = new ArrayList<>();
		if (lead.getLeadNotes() != null) {
			for (LeadNotes ln : lead.getLeadNotes()) {
				if (!"".equals(ln.getNotes().trim())) {
					 if(ln.getUser().getId().intValue() == userId.intValue()){
						 currentUserLeadNotes = ln.getNotes();
					 }
					ln.setLead(currentLeadMembership);
					leadNotes.add(ln);
				}
			}
		}

		if (leadNotes.size() > 0) {
			currentLeadMembership.getLeadNotes().clear();
			currentLeadMembership.getLeadNotes().addAll(leadNotes);
		}

		

		if (!"New".equalsIgnoreCase(lead.getStatus().getDescription())) {
			  agntLeadAppointList = lead.getAgentLeadAppointmentList();
			currentLeadMembership.getAgentLeadAppointmentList().clear();
			//currentLeadMembership.getAgentLeadAppointmentList().addAll(agntLeadAppointList);
			for (AgentLeadAppointment ala : agntLeadAppointList) {
			
					ala.setLead(currentLeadMembership);
					finalAgentLeadAppointList.add(ala);
			}
			if (finalAgentLeadAppointList.size() > 0) {
				currentLeadMembership.getAgentLeadAppointmentList().clear();
				currentLeadMembership.getAgentLeadAppointmentList().addAll(finalAgentLeadAppointList);
			}
			
		}


		leadService.updateLeadMembership(currentLeadMembership);

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String currentTime = sdf.format((new Date()).getTime());

		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String currentLocalTime = sdf1.format((new Date()).getTime());
        Contact cnt = currentLeadMembership.getContact();
		String address=  Stream.of(cnt.getAddress1(), cnt.getAddress2(), cnt.getCity(), cnt.getStateCode().getShortName(), cnt.getZipCode().toString())
				          .filter(s -> s != null && !s.isEmpty())
				          .collect(Collectors.joining(","));
		
		final Email mail = new Email();
		mail.setEmailFrom(loginUser.getContact().getEmail());
		mail.setEmailCc(loginUser.getContact().getEmail());
		
		Map<String, Object> emailAttributes = new HashMap<>();
		emailAttributes.put("currentUser", loginUser.getName());
		emailAttributes.put("currentUserRole", roleName);
		emailAttributes.put("currentLocalTime", currentLocalTime);
		emailAttributes.put("firstName", currentLeadMembership.getFirstName());
		emailAttributes.put("lastName", currentLeadMembership.getLastName());
		emailAttributes.put("location",	address);
		
		
		if( agntLeadAppointList.size() > 0)  {
			
				String toEmailIds = agntLeadAppointList.stream().map(la -> la.getUser().getContact().getEmail())
						.collect(Collectors.joining(";"));
				AgentLeadAppointment agntLeadAppointment = agntLeadAppointList.stream()
						.filter(ala -> ala.getActiveInd() == 'Y').findAny().get();
				
				if(agntLeadAppointment.getAppointmentTime().compareTo(Calendar.getInstance().getTime()) > 0){
					
					String agentName = agntLeadAppointment.getUser().getName();
					String agentUsername =  agntLeadAppointment.getUser().getUsername();
					String agentPhone = (agntLeadAppointment.getUser().getContact().getHomePhone() == null)?
							agntLeadAppointment.getUser().getContact().getMobilePhone(): agntLeadAppointment.getUser().getContact().getHomePhone();
					
					String attachmentKey = agentUsername+ agentPhone.substring(6);
					
					String appointmentTime = sdf.format(agntLeadAppointment.getAppointmentTime().getTime());
					Date calApptTime = agntLeadAppointment.getAppointmentTime();
					cal.setTime(calApptTime);
					cal.add(Calendar.MINUTE, 60);
					String appointmentEndTime = sdf.format(cal.getTime());
					String careCoordinator = agntLeadAppointment.getCreatedBy();
					String appointmentLocalTime = sdf1.format(agntLeadAppointment.getAppointmentTime().getTime());

					emailAttributes.put("agent", agentName);
					emailAttributes.put("careCoordinator", careCoordinator);
					emailAttributes.put("appointmentStartTime", appointmentTime);
					emailAttributes.put("appointmentEndTime", appointmentEndTime);
					emailAttributes.put("currentTime", currentTime);
					emailAttributes.put("notes", currentUserLeadNotes);					
					emailAttributes.put("appointmentLocalTime", appointmentLocalTime);
					emailAttributes.put("attachmentKey", attachmentKey);
					
					Set<FileUploadContent> leadConsentForms = new HashSet<>();
					FileUpload  fileupload = currentLeadMembership.getFileUpload();
					if(fileupload != null){
						Integer consentFormId = fileupload.getId();
						FileUploadContent leadConsentForm = fileUploadContentService.findById(consentFormId);
						if (leadConsentForm == null) {
							logger.error("leadConsentForm with id {} not found.", id);
						}else{
							leadConsentForms.add(leadConsentForm);
							emailAttributes.put("attachments",leadConsentForms);
						}
					}
					
					
					
					String emailTemplateFileName = "agent_lead_assignment_email_template_" + roleName + ".txt";
					mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));
					mail.setModel(emailAttributes);
					mail.setSubject("Scheduled an Appointment with lead: "+currentLeadMembership.getLastName()+","+currentLeadMembership.getFirstName());
					mail.setEmailTo(toEmailIds);
					
					emailService.sendMailWithAttachment(mail);
				}else {
					 toEmailIds =  configProp.getCoordinatorEmail();  
					String careCoordinator = configProp.getCoordinatorName();
					String emailTemplateFileName = "lead_update_email_template_" + roleName + ".txt";
					
					emailAttributes.put("careCoordinator", careCoordinator);
					mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));
					mail.setModel(emailAttributes);
					mail.setSubject("Updated Lead details for "+currentLeadMembership.getLastName()+","+currentLeadMembership.getFirstName());
					mail.setEmailTo(toEmailIds);
					
					emailService.sendMail(mail);
				}
				
		} else {
			String toEmailIds =  configProp.getCoordinatorEmail();  
			String careCoordinator = configProp.getCoordinatorName();
			String emailTemplateFileName = "lead_update_email_template_" + roleName + ".txt";
			
			emailAttributes.put("careCoordinator", careCoordinator);
			mail.setBody(emailService.geContentFromTemplate(emailAttributes, emailTemplateFileName));
			mail.setModel(emailAttributes);
			mail.setSubject("Updated Lead details for "+currentLeadMembership.getLastName()+","+currentLeadMembership.getFirstName());
			mail.setEmailTo(toEmailIds);
			
			emailService.sendMail(mail);
		}

		return new ResponseEntity<LeadMembership>(currentLeadMembership, HttpStatus.OK);
	}


	// ------------------- Delete a
	// LeadMembership-----------------------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLeadMembership(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting LeadMembership with id {}", id);

		LeadMembership lead = leadService.findById(id);
		if (lead == null) {
			logger.error("Unable to delete. LeadMembership with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("Unable to delete. LeadMembership with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		leadService.deleteLeadMembershipById(id);
		return new ResponseEntity<LeadMembership>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All
	// LeadMemberships-----------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/lead/", method = RequestMethod.DELETE)
	public ResponseEntity<LeadMembership> deleteAllLeadMemberships() {
		logger.info("Deleting All LeadMemberships");

		leadService.deleteAllLeadMemberships();
		return new ResponseEntity<LeadMembership>(HttpStatus.NO_CONTENT);
	}

}