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

import com.pfchoice.springboot.model.LeadLanguage;
import com.pfchoice.springboot.service.LeadLanguageService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LeadLanguageController {

	public static final Logger logger = LoggerFactory.getLogger(LeadLanguageController.class);

	@Autowired
	LeadLanguageService leadLanguageService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All LeadLanguages---------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT"  })
	@RequestMapping(value = "/leadLanguage/", method = RequestMethod.GET)
	public ResponseEntity<List<LeadLanguage>> listAllLeadLanguages() {
		List<LeadLanguage> leadLanguages = leadLanguageService.findAllLeadLanguages();
		if (leadLanguages.isEmpty()) {
			System.out.println("no leadLanguages");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are leadLanguages");
		return new ResponseEntity<List<LeadLanguage>>(leadLanguages, HttpStatus.OK);
	}

	// -------------------Retrieve Single LeadLanguage------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadLanguage/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLeadLanguage(@PathVariable("id") byte id) {
		logger.info("Fetching LeadLanguage with id {}", id);
		LeadLanguage leadLanguage = leadLanguageService.findById(id);
		if (leadLanguage == null) {
			logger.error("LeadLanguage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("LeadLanguage with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeadLanguage>(leadLanguage, HttpStatus.OK);
	}

	// -------------------Create a LeadLanguage-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadLanguage/", method = RequestMethod.POST)
	public ResponseEntity<?> createLeadLanguage(@RequestBody LeadLanguage leadLanguage, UriComponentsBuilder ucBuilder) {
		logger.info("Creating LeadLanguage : {}", leadLanguage);

		if (leadLanguageService.isLeadLanguageExist(leadLanguage)) {
			logger.error("Unable to create. A LeadLanguage with name {} already exist", leadLanguage.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A LeadLanguage with name " + 
			leadLanguage.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		leadLanguage.setCreatedBy("sarath");
		leadLanguage.setUpdatedBy("sarath");
		leadLanguageService.saveLeadLanguage(leadLanguage);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/leadLanguage/{id}").buildAndExpand(leadLanguage.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a LeadLanguage ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadLanguage/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLeadLanguage(@PathVariable("id") byte id, @RequestBody LeadLanguage leadLanguage) {
		logger.info("Updating LeadLanguage with id {}", id);

		LeadLanguage currentLeadLanguage = leadLanguageService.findById(id);

		if (currentLeadLanguage == null) {
			logger.error("Unable to update. LeadLanguage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. LeadLanguage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentLeadLanguage.setDescription(leadLanguage.getDescription());

		leadLanguageService.updateLeadLanguage(currentLeadLanguage);
		return new ResponseEntity<LeadLanguage>(currentLeadLanguage, HttpStatus.OK);
	}

	// ------------------- Delete a LeadLanguage-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadLanguage/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLeadLanguage(@PathVariable("id") byte id) {
		logger.info("Fetching & Deleting LeadLanguage with id {}", id);

		LeadLanguage leadLanguage = leadLanguageService.findById(id);
		if (leadLanguage == null) {
			logger.error("Unable to delete. LeadLanguage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. LeadLanguage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		leadLanguageService.deleteLeadLanguageById(id);
		return new ResponseEntity<LeadLanguage>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All LeadLanguages-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/leadLanguage/", method = RequestMethod.DELETE)
	public ResponseEntity<LeadLanguage> deleteAllLeadLanguages() {
		logger.info("Deleting All LeadLanguages");

		leadLanguageService.deleteAllLeadLanguages();
		return new ResponseEntity<LeadLanguage>(HttpStatus.NO_CONTENT);
	}

}