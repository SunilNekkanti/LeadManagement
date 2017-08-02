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

import com.pfchoice.springboot.model.ActivityType;
import com.pfchoice.springboot.service.ActivityTypeService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActivityTypeController {

	public static final Logger logger = LoggerFactory.getLogger(ActivityTypeController.class);

	@Autowired
	ActivityTypeService activityTypeService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All ActivityTypes---------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR" })
	@RequestMapping(value = "/activityType/", method = RequestMethod.GET)
	public ResponseEntity<List<ActivityType>> listAllActivityTypes() {
		List<ActivityType> activityTypes = activityTypeService.findAllActivityTypes();
		if (activityTypes.isEmpty()) {
			System.out.println("no activityTypes");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are activityTypes");
		return new ResponseEntity<List<ActivityType>>(activityTypes, HttpStatus.OK);
	}

	// -------------------Retrieve Single ActivityType------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/activityType/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getActivityType(@PathVariable("id") int id) {
		logger.info("Fetching ActivityType with id {}", id);
		ActivityType activityType = activityTypeService.findById(id);
		if (activityType == null) {
			logger.error("ActivityType with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("ActivityType with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ActivityType>(activityType, HttpStatus.OK);
	}

	// -------------------Create a ActivityType-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/activityType/", method = RequestMethod.POST)
	public ResponseEntity<?> createActivityType(@RequestBody ActivityType activityType, UriComponentsBuilder ucBuilder) {
		logger.info("Creating ActivityType : {}", activityType);

		if (activityTypeService.isActivityTypeExist(activityType)) {
			logger.error("Unable to create. A ActivityType with name {} already exist", activityType.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A ActivityType with name " + 
			activityType.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		activityType.setCreatedBy("sarath");
		activityType.setUpdatedBy("sarath");
		activityTypeService.saveActivityType(activityType);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/activityType/{id}").buildAndExpand(activityType.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a ActivityType ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/activityType/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateActivityType(@PathVariable("id") int id, @RequestBody ActivityType activityType) {
		logger.info("Updating ActivityType with id {}", id);

		ActivityType currentActivityType = activityTypeService.findById(id);

		if (currentActivityType == null) {
			logger.error("Unable to update. ActivityType with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. ActivityType with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentActivityType.setId(activityType.getId());
		currentActivityType.setDescription(activityType.getDescription());

		activityTypeService.updateActivityType(currentActivityType);
		return new ResponseEntity<ActivityType>(currentActivityType, HttpStatus.OK);
	}

	// ------------------- Delete a ActivityType-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/activityType/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteActivityType(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting ActivityType with id {}", id);

		ActivityType activityType = activityTypeService.findById(id);
		if (activityType == null) {
			logger.error("Unable to delete. ActivityType with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. ActivityType with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		activityTypeService.deleteActivityTypeById(id);
		return new ResponseEntity<ActivityType>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All ActivityTypes-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/activityType/", method = RequestMethod.DELETE)
	public ResponseEntity<ActivityType> deleteAllActivityTypes() {
		logger.info("Deleting All ActivityTypes");

		activityTypeService.deleteAllActivityTypes();
		return new ResponseEntity<ActivityType>(HttpStatus.NO_CONTENT);
	}

}