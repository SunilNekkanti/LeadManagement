package com.pfchoice.springboot.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.AgentLeadAppointment;
import com.pfchoice.springboot.model.Email;
import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.repositories.specifications.LeadSpecifications;
import com.pfchoice.springboot.service.EmailService;
import com.pfchoice.springboot.service.LeadMembershipService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LeadController {

	public static final Logger logger = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	LeadMembershipService leadService; //Service which will do all data retrieval/manipulation work
	

	@Autowired
	EmailService emailService;
	
	// -------------------Retrieve All LeadMemberships---------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR"  })
	@RequestMapping(value = "/lead/", method = RequestMethod.GET)
	public ResponseEntity<Page<LeadMembership>> listAllLeadMemberships(@RequestParam(value = "page", required = false) int pageNo,  @RequestParam(value = "size", required = false) int pageSize,@RequestParam(value = "search", required = false) String search) throws MessagingException, IOException {
		
/*		Email eParams = new Email();
		eParams.setEmailTo("skumar@pfchoice.com");
		eParams.setBody("testing email functionaliity");
		eParams.setEmailFrom("skumar@pfchoice.com");
		eParams.setEmailCc("mohangbvn@gmail.com");
		
		emailService.sendMailWithAttachment(eParams);*/
		
		PageRequest pageRequest = new PageRequest(pageNo,pageSize );
		Specification<LeadMembership> spec = new LeadSpecifications(search);
		Page<LeadMembership> leads = leadService.findAllLeadMembershipsByPage(spec, pageRequest);
		if (leads.getTotalElements() == 0) {
			System.out.println("no leads");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are leads");
		return new ResponseEntity<Page<LeadMembership>>(leads, HttpStatus.OK);
	}

	// -------------------Retrieve Single LeadMembership------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR"   })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLeadMembership(@PathVariable("id") int id) {
		logger.info("Fetching LeadMembership with id {}", id);
		LeadMembership lead = leadService.findById(id);
		if (lead == null) {
			logger.error("LeadMembership with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("LeadMembership with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeadMembership>(lead, HttpStatus.OK);
	}

	// -------------------Create a LeadMembership-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR"  })
	@RequestMapping(value = "/lead/", method = RequestMethod.POST)
	public ResponseEntity<?> createLeadMembership(@RequestBody LeadMembership lead, UriComponentsBuilder ucBuilder) {
		logger.info("Creating LeadMembership : {}", lead);

		if (leadService.isLeadMembershipExists(lead.getFirstName(),lead.getLastName(),lead.getDob())) {
			logger.error("Unable to create. A LeadMembership with name {} already exist", lead.getFirstName()+ lead.getLastName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A LeadMembership with name " + 
					lead.getFirstName() +" "+ lead.getLastName() + " already exist."),HttpStatus.CONFLICT);
		}
		lead.setCreatedBy("sarath");
		lead.setUpdatedBy("sarath");
		leadService.saveLeadMembership(lead);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/lead/{id}").buildAndExpand(lead.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a LeadMembership ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR"  })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLeadMembership(@PathVariable("id") int id, @RequestBody LeadMembership lead) {
		logger.info("Updating LeadMembership with id {}", id);

		LeadMembership currentLeadMembership = leadService.findById(id);

		if (currentLeadMembership == null) {
			logger.error("Unable to update. LeadMembership with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. LeadMembership with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
      
		currentLeadMembership.setFirstName(lead.getFirstName());
		currentLeadMembership.setLastName(lead.getLastName());
		currentLeadMembership.setCountyCode(lead.getCountyCode());
		currentLeadMembership.setDob(lead.getDob());
		currentLeadMembership.setEthinicCode(lead.getEthinicCode());
		currentLeadMembership.setPlanType(lead.getPlanType());
		currentLeadMembership.setGenderId(lead.getGenderId());
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
		
		List<AgentLeadAppointment> agntLeadAppointList = lead.getAgentLeadAppointmentList();
	
		agntLeadAppointList.forEach(agntleadAppt -> {System.out.println("testing agntleadAppt"+agntleadAppt);	
		System.out.println("testing agntleadAppt"+agntleadAppt.toString());}
		);
		
		//currentLeadMembership.getAgentLeadAppointmentList().clear();
		
		currentLeadMembership.setAgentLeadAppointmentList(agntLeadAppointList);  
		System.out.println("currentLeadMembership.getAgentLeadAppointmentList().size() =====3step" +currentLeadMembership.getAgentLeadAppointmentList().size());
		
		leadService.updateLeadMembership(currentLeadMembership);
		return new ResponseEntity<LeadMembership>(currentLeadMembership, HttpStatus.OK);
	}

	// ------------------- Delete a LeadMembership-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/lead/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLeadMembership(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting LeadMembership with id {}", id);

		LeadMembership lead = leadService.findById(id);
		if (lead == null) {
			logger.error("Unable to delete. LeadMembership with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. LeadMembership with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		leadService.deleteLeadMembershipById(id);
		return new ResponseEntity<LeadMembership>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All LeadMemberships-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/lead/", method = RequestMethod.DELETE)
	public ResponseEntity<LeadMembership> deleteAllLeadMemberships() {
		logger.info("Deleting All LeadMemberships");

		leadService.deleteAllLeadMemberships();
		return new ResponseEntity<LeadMembership>(HttpStatus.NO_CONTENT);
	}

}