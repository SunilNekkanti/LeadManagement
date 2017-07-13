package com.pfchoice.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.LeadNotes;
import com.pfchoice.springboot.service.LeadNotesService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LeadNotesController {

	public static final Logger logger = LoggerFactory.getLogger(LeadNotesController.class);

	@Autowired
	LeadNotesService leadNotesService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All LeadNotess---------------------------------------------

	
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT" })
	@RequestMapping(value = "/leadNotes/", method = RequestMethod.GET)
	public ResponseEntity<List<LeadNotes>> listAllLeadNotess() {
		List<LeadNotes> leadNotess = leadNotesService.findAllLeadNotes();
		if (leadNotess.isEmpty()) {
			System.out.println("no leadNotess");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are leadNotess");
		return new ResponseEntity<List<LeadNotes>>(leadNotess, HttpStatus.OK);
	}

	// -------------------Retrieve Single LeadNotes------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadNotes/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLeadNotes(@PathVariable("id") int id) {
		logger.info("Fetching LeadNotes with id {}", id);
		LeadNotes leadNotes = leadNotesService.findById(id);
		if (leadNotes == null) {
			logger.error("LeadNotes with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("LeadNotes with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeadNotes>(leadNotes, HttpStatus.OK);
	}

	// -------------------Create a LeadNotes-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadNotes/", method = RequestMethod.POST)
	public ResponseEntity<?> createLeadNotes(@RequestBody LeadNotes leadNotes, UriComponentsBuilder ucBuilder) {
		logger.info("Creating LeadNotes : {}", leadNotes);

		if (leadNotesService.isLeadNotesExist(leadNotes)) {
			logger.error("Unable to create. A LeadNotes with name {} already exist", leadNotes.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A LeadNotes with name " + 
			leadNotes.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		leadNotes.setCreatedBy("sarath");
		leadNotes.setUpdatedBy("sarath");
		leadNotesService.saveLeadNotes(leadNotes);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/leadNotes/{id}").buildAndExpand(leadNotes.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a LeadNotes ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadNotes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLeadNotes(@PathVariable("id") int id, @RequestBody LeadNotes leadNotes) {
		logger.info("Updating LeadNotes with id {}", id);

		LeadNotes currentLeadNotes = leadNotesService.findById(id);

		if (currentLeadNotes == null) {
			logger.error("Unable to update. LeadNotes with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. LeadNotes with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentLeadNotes.setNotes(leadNotes.getNotes());

		leadNotesService.updateLeadNotes(currentLeadNotes);
		return new ResponseEntity<LeadNotes>(currentLeadNotes, HttpStatus.OK);
	}

	// ------------------- Delete a LeadNotes-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadNotes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLeadNotes(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting LeadNotes with id {}", id);

		LeadNotes leadNotes = leadNotesService.findById(id);
		if (leadNotes == null) {
			logger.error("Unable to delete. LeadNotes with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. LeadNotes with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		leadNotesService.deleteLeadNotesById(id);
		return new ResponseEntity<LeadNotes>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All LeadNotess-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadNotes/", method = RequestMethod.DELETE)
	public ResponseEntity<LeadNotes> deleteAllLeadNotess() {
		logger.info("Deleting All LeadNotess");

		leadNotesService.deleteAllLeadNotes();
		return new ResponseEntity<LeadNotes>(HttpStatus.NO_CONTENT);
	}

}