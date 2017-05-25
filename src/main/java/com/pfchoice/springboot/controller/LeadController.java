package com.pfchoice.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.service.LeadMembershipService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LeadController {

	public static final Logger logger = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	LeadMembershipService leadService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All LeadMemberships---------------------------------------------

	
	
	@RequestMapping(value = "/lead/", method = RequestMethod.GET)
	public ResponseEntity<List<LeadMembership>> listAllLeadMemberships() {
		List<LeadMembership> leads = leadService.findAllLeadMemberships();
		if (leads.isEmpty()) {
			System.out.println("no leads");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are leads");
		return new ResponseEntity<List<LeadMembership>>(leads, HttpStatus.OK);
	}

	// -------------------Retrieve Single LeadMembership------------------------------------------

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
		currentLeadMembership.setGenderId(lead.getGenderId());
		currentLeadMembership.setHasMedicaid(lead.getHasMedicaid());
		currentLeadMembership.setHasMedicare(lead.getHasMedicare());
		currentLeadMembership.setHasDisability(lead.getHasDisability());
		currentLeadMembership.setStatus(lead.getStatus());
		leadService.updateLeadMembership(currentLeadMembership);
		return new ResponseEntity<LeadMembership>(currentLeadMembership, HttpStatus.OK);
	}

	// ------------------- Delete a LeadMembership-----------------------------------------

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

	@RequestMapping(value = "/lead/", method = RequestMethod.DELETE)
	public ResponseEntity<LeadMembership> deleteAllLeadMemberships() {
		logger.info("Deleting All LeadMemberships");

		leadService.deleteAllLeadMemberships();
		return new ResponseEntity<LeadMembership>(HttpStatus.NO_CONTENT);
	}

}