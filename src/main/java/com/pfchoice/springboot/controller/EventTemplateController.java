package com.pfchoice.springboot.controller;

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

import com.pfchoice.springboot.model.EventTemplate;
import com.pfchoice.springboot.repositories.specifications.EventTemplateSpecifications;
import com.pfchoice.springboot.service.EventTemplateService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EventTemplateController {

	public static final Logger logger = LoggerFactory.getLogger(EventTemplateController.class);

	@Autowired
	EventTemplateService eventTemplateService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All EventTemplates---------------------------------------------

	
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/", method = RequestMethod.GET)
	public ResponseEntity<Page<EventTemplate>> listAllEventTemplates(@RequestParam("page") Integer pageNo,  @RequestParam("size") Integer pageSize, @RequestParam(value = "search", required = false) String search) {
		
		pageNo = (pageNo == null)?0:pageNo;
		pageSize = (pageSize == null)?1000:pageSize;
		
		PageRequest pageRequest = new PageRequest(pageNo,pageSize );
		Specification<EventTemplate> spec =null ;
		if(!"".equals(search))
		 spec = new EventTemplateSpecifications(search);
		Page<EventTemplate> eventTemplates = eventTemplateService.findAllEventTemplatesByPage(spec, pageRequest);
		
		if (eventTemplates.getTotalElements() == 0) {
			System.out.println("no eventTemplates");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are eventTemplates");
		return new ResponseEntity<Page<EventTemplate>>(eventTemplates, HttpStatus.OK);
	}

	// -------------------Retrieve Single EventTemplate------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEventTemplate(@PathVariable("id") int id) {
		logger.info("Fetching EventTemplate with id {}", id);
		EventTemplate eventTemplate = eventTemplateService.findById(id);
		if (eventTemplate == null) {
			logger.error("EventTemplate with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("EventTemplate with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EventTemplate>(eventTemplate, HttpStatus.OK);
	}

	// -------------------Create a EventTemplate-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/", method = RequestMethod.POST)
	public ResponseEntity<?> createEventTemplate(@RequestBody EventTemplate eventTemplate, UriComponentsBuilder ucBuilder) {
		logger.info("Creating EventTemplate : {}", eventTemplate);

		if (eventTemplateService.isEventTemplateExists(eventTemplate.getName())) {
			logger.error("Unable to create. A EventTemplate with name {} already exist", eventTemplate.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A EventTemplate with name " + 
					eventTemplate.getName()  + " already exist."),HttpStatus.CONFLICT);
		}
		
		eventTemplateService.saveEventTemplate(eventTemplate);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/eventTemplate/{id}").buildAndExpand(eventTemplate.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a EventTemplate ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEventTemplate(@PathVariable("id") int id, @RequestBody EventTemplate eventTemplate) {
		logger.info("Updating EventTemplate with id {}", id);

		EventTemplate currentEventTemplate = eventTemplateService.findById(id);

		if (currentEventTemplate == null) {
			logger.error("Unable to update. EventTemplate with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. EventTemplate with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
      
		currentEventTemplate.setName(eventTemplate.getName());
		currentEventTemplate.setAddress1(eventTemplate.getAddress1());
		currentEventTemplate.setAddress2(eventTemplate.getAddress2());
		currentEventTemplate.setCity(eventTemplate.getCity());
		currentEventTemplate.setState(eventTemplate.getState());
		currentEventTemplate.setZipCode(eventTemplate.getZipCode());
		currentEventTemplate.setContactPerson(eventTemplate.getContactPerson());
		currentEventTemplate.setContactPhone(eventTemplate.getContactPhone());
		eventTemplateService.updateEventTemplate(currentEventTemplate);
		
		return new ResponseEntity<EventTemplate>(currentEventTemplate, HttpStatus.OK);
	}

	// ------------------- Delete a EventTemplate-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEventTemplate(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting EventTemplate with id {}", id);

		EventTemplate eventTemplate = eventTemplateService.findById(id);
		if (eventTemplate == null) {
			logger.error("Unable to delete. EventTemplate with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. EventTemplate with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		eventTemplateService.deleteEventTemplateById(id);
		return new ResponseEntity<EventTemplate>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All EventTemplates-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/eventTemplate/", method = RequestMethod.DELETE)
	public ResponseEntity<EventTemplate> deleteAllEventTemplates() {
		logger.info("Deleting All EventTemplates");

		eventTemplateService.deleteAllEventTemplates();
		return new ResponseEntity<EventTemplate>(HttpStatus.NO_CONTENT);
	}

}