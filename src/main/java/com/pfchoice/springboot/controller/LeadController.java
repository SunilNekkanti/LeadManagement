package com.pfchoice.springboot.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

import com.pfchoice.springboot.model.AgentLeadAppointment;
import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.model.LeadNotes;
import com.pfchoice.springboot.model.User;
import com.pfchoice.springboot.repositories.specifications.LeadSpecifications;
import com.pfchoice.springboot.service.EmailService;
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
	EmailService emailService;

	// -------------------Retrieve All
	// LeadMemberships---------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/lead/", method = RequestMethod.GET)
	public ResponseEntity<Page<LeadMembership>> listAllLeadMemberships(
			@RequestParam(value = "page", required = false) Integer pageNo,
			@RequestParam(value = "size", required = false) Integer pageSize,
			@RequestParam(value = "search", required = false) String search,
			@ModelAttribute("roleName") String roleName) throws MessagingException, IOException {

		pageNo = (pageNo == null)?0:pageNo;
		pageSize = (pageSize == null)?10:pageSize;
		
		PageRequest pageRequest = new PageRequest(pageNo, pageSize);
		Specification<LeadMembership> spec = new LeadSpecifications(search);
		Page<LeadMembership> leads = leadService.findAllLeadMembershipsByPage(spec, pageRequest);
		if (leads.getTotalElements() == 0) {
			System.out.println("no leads");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Page<LeadMembership>>(leads, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// LeadMembership------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
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
	@Secured({ "ROLE_ADMIN", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/lead/", method = RequestMethod.POST)
	public ResponseEntity<?> createLeadMembership(@RequestBody LeadMembership lead, UriComponentsBuilder ucBuilder,
			@ModelAttribute("userId") Integer userId) throws Exception {
		logger.info("Creating LeadMembership : {}", lead);

		if (leadService.isLeadMembershipExists(lead.getFirstName(), lead.getLastName(), lead.getDob())) {
			logger.error("Unable to create. A LeadMembership with name {} already exist",
					lead.getFirstName() + lead.getLastName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A LeadMembership with name "
					+ lead.getFirstName() + " " + lead.getLastName() + " already exist."), HttpStatus.CONFLICT);
		}
		StringBuffer emailBody = new StringBuffer();
		User user = userService.findById(userId);
		List<LeadNotes> leadNotes = lead.getLeadNotes();
		leadNotes.forEach(ln -> {
			ln.setUser(user);
			ln.setLead(lead);
			emailBody.append(ln.getNotes());
		});

		lead.setLeadNotes(leadNotes);
		leadService.saveLeadMembership(lead);

		/*String toEmailIds = lead.getEvent().getRepresentatives().stream().map(rep -> rep.getEmail())
				.collect(Collectors.joining(","));
*/
		Email mail = new Email();
		mail.setEmailTo("skumar@pfchoice.com");
		mail.setEmailFrom("skumar@pfchoice.com");
		mail.setEmailCc("skumar@pfchoice.com");
		mail.setSubject("New lead created");
		
		mail.setBody(emailService.geContentFromTemplate(lead, "lead_create_email_template.txt"));
		emailService.sendMail(mail);
		// emailService.sendMailWithAttachment(eParams);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/lead/{id}").buildAndExpand(lead.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a LeadMembership
	// ------------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLeadMembership(@PathVariable("id") int id, @RequestBody LeadMembership lead,
			@ModelAttribute("userId") Integer userId,@ModelAttribute("roleName") String roleName) throws MessagingException, InterruptedException, IOException {
		logger.info("Updating LeadMembership with id {}", id);

		LeadMembership currentLeadMembership = leadService.findById(id);

		if (currentLeadMembership == null) {
			logger.error("Unable to update. LeadMembership with id {} not found.", id);
			return new ResponseEntity(
					new CustomErrorType("Unable to upate. LeadMembership with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentLeadMembership.setFirstName(lead.getFirstName());
		currentLeadMembership.setLastName(lead.getLastName());
		currentLeadMembership.setCountyCode(lead.getCountyCode());
		currentLeadMembership.setDob(lead.getDob());
		currentLeadMembership.setEthinicCode(lead.getEthinicCode());
		currentLeadMembership.setPlanType(lead.getPlanType());
		currentLeadMembership.setGender(lead.getGender());
		currentLeadMembership.setStatus(lead.getStatus());
		currentLeadMembership.setLanguage(lead.getLanguage());
		currentLeadMembership.setStatus(lead.getStatus());
		currentLeadMembership.setEmail(lead.getEmail());
		currentLeadMembership.setHomePhone(lead.getHomePhone());
		currentLeadMembership.setMobilePhone(lead.getMobilePhone());
		currentLeadMembership.setAddress1(lead.getAddress1());
		currentLeadMembership.setAddress2(lead.getAddress2());
		currentLeadMembership.setCity(lead.getCity());
		currentLeadMembership.setStateCode(lead.getStateCode());
		currentLeadMembership.setZipCode(lead.getZipCode());
		
		
		User user = userService.findById(userId);

		List<LeadNotes> leadNotes = new ArrayList<>();
		for (LeadNotes ln : lead.getLeadNotes()) {
			ln.setUser(user);
			ln.setLead(currentLeadMembership);
			leadNotes.add(ln);
		}
		
		currentLeadMembership.getLeadNotes().clear();
		currentLeadMembership.getLeadNotes().addAll(leadNotes);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	
		List<AgentLeadAppointment> agntLeadAppointList = lead.getAgentLeadAppointmentList();
		
		currentLeadMembership.getAgentLeadAppointmentList().clear();
		currentLeadMembership.getAgentLeadAppointmentList().addAll(agntLeadAppointList);

		leadService.updateLeadMembership(currentLeadMembership);

		String toEmailIds = agntLeadAppointList.stream().map(la -> la.getUser().getEmail())
				.collect(Collectors.joining(","));
		String agentName = agntLeadAppointList.size() > 0 ? agntLeadAppointList.stream().filter(ala -> ala.getActiveInd()=='Y').findAny().get().getUser().getUsername() : "";
		Calendar calApptTime = (agntLeadAppointList.size() > 0)
				?  agntLeadAppointList.stream().filter(ala -> ala.getActiveInd()=='Y').findAny().get().getAppointmentTime() : Calendar.getInstance();
			
		String appointmentTime = agntLeadAppointList.size() > 0
				? sdf.format(agntLeadAppointList.stream().filter(ala -> ala.getActiveInd()=='Y').findAny().get().getAppointmentTime().getTime()) : new String("");
		calApptTime.add(Calendar.MINUTE, 60);		
		String appointmentEndTime = agntLeadAppointList.size() > 0
						? sdf.format(calApptTime.getTime()) : new String("");
		 
		String address = lead.getAddress1() + "\n" + lead.getAddress2() + "\n" + lead.getCity() + " ,"
				+ lead.getStateCode() + " ," + lead.getZipCode();
		String leadNotesString = lead.getLeadNotes().stream().sorted((a,b) -> -1).findFirst().get().getNotes();
	    String careCoordinator =  agntLeadAppointList.stream().filter(ala -> ala.getActiveInd()=='Y').findAny().get().getCreatedBy();			
		String currentTime = sdf.format((new Date()).getTime());
		

		calApptTime.setTimeZone(TimeZone.getTimeZone("UTC"));

		System.out.println(sdf.format(calApptTime.getTime()));
		
		
		Email mail = new Email();
		mail.setEmailTo(toEmailIds);
		mail.setEmailFrom("skumar@pfchoice.com");
		mail.setEmailCc(user.getEmail());
		mail.setSubject("Agent Lead Assignment");
		Map<String, Object> emailAttributes = new HashMap<>();
		emailAttributes.put("agent", agentName);
		emailAttributes.put("currentUser", user.getUsername());
		emailAttributes.put("careCoordinator", careCoordinator);
		emailAttributes.put("firstName", lead.getFirstName());
		emailAttributes.put("lastName", lead.getLastName());
		emailAttributes.put("notes", leadNotesString);
		emailAttributes.put("appointmentStartTime", appointmentTime);
		emailAttributes.put("appointmentEndTime", appointmentEndTime);
		emailAttributes.put("currentTime", currentTime);
		emailAttributes.put("location", address);
		
		mail.setModel(emailAttributes);
		String emailTemplateFileName = "agent_lead_assignment_email_template_"+roleName+".txt";
		
		mail.setBody(emailService.geContentFromTemplate(emailAttributes,emailTemplateFileName ));
		if("ROLE_CARE_COORDINATOR".equals(roleName)){
			emailService.sendMailWithAttachment(mail);
		} else{
			emailService.sendMail(mail);
		}
		// emailService.sendMailWithAttachment(eParams);

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