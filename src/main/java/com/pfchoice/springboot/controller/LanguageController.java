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

import com.pfchoice.springboot.model.Language;
import com.pfchoice.springboot.service.LanguageService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class LanguageController {

	public static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

	@Autowired
	LanguageService LanguageService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Languages---------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT"  })
	@RequestMapping(value = "/language/", method = RequestMethod.GET)
	public ResponseEntity<List<Language>> listAllLanguages() {
		List<Language> Languages = LanguageService.findAllLanguages();
		if (Languages.isEmpty()) {
			System.out.println("no Languages");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are Languages");
		return new ResponseEntity<List<Language>>(Languages, HttpStatus.OK);
	}

	// -------------------Retrieve Single Language------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/language/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLanguage(@PathVariable("id") byte id) {
		logger.info("Fetching Language with id {}", id);
		Language Language = LanguageService.findById(id);
		if (Language == null) {
			logger.error("Language with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Language with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Language>(Language, HttpStatus.OK);
	}

	// -------------------Create a Language-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/language/", method = RequestMethod.POST)
	public ResponseEntity<?> createLanguage(@RequestBody Language Language, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Language : {}", Language);

		if (LanguageService.isLanguageExist(Language)) {
			logger.error("Unable to create. A Language with name {} already exist", Language.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Language with name " + 
			Language.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		Language.setCreatedBy("sarath");
		Language.setUpdatedBy("sarath");
		LanguageService.saveLanguage(Language);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/Language/{id}").buildAndExpand(Language.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Language ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/language/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLanguage(@PathVariable("id") byte id, @RequestBody Language Language) {
		logger.info("Updating Language with id {}", id);

		Language currentLanguage = LanguageService.findById(id);

		if (currentLanguage == null) {
			logger.error("Unable to update. Language with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Language with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentLanguage.setDescription(Language.getDescription());

		LanguageService.updateLanguage(currentLanguage);
		return new ResponseEntity<Language>(currentLanguage, HttpStatus.OK);
	}

	// ------------------- Delete a Language-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/language/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLanguage(@PathVariable("id") byte id) {
		logger.info("Fetching & Deleting Language with id {}", id);

		Language Language = LanguageService.findById(id);
		if (Language == null) {
			logger.error("Unable to delete. Language with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Language with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		LanguageService.deleteLanguageById(id);
		return new ResponseEntity<Language>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Languages-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/language/", method = RequestMethod.DELETE)
	public ResponseEntity<Language> deleteAllLanguages() {
		logger.info("Deleting All Languages");

		LanguageService.deleteAllLanguages();
		return new ResponseEntity<Language>(HttpStatus.NO_CONTENT);
	}

}