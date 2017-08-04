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

import com.pfchoice.springboot.model.Brokerage;
import com.pfchoice.springboot.service.BrokerageService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BrokerageController {

	public static final Logger logger = LoggerFactory.getLogger(BrokerageController.class);

	@Autowired
	BrokerageService brokerageService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Brokerages---------------------------------------------

	
	@Secured({  "ROLE_ADMIN", "ROLE_AGENT","ROLE_EVENT_COORDINATOR","ROLE_CARE_COORDINATOR","ROLE_MANAGER" })
	@RequestMapping(value = "/brokerage/", method = RequestMethod.GET)
	public ResponseEntity<List<Brokerage>> listAllBrokerages() {
		List<Brokerage> brokerages = brokerageService.findAllBrokerages();
		if (brokerages.isEmpty()) {
			System.out.println("no brokerages");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are brokerages");
		return new ResponseEntity<List<Brokerage>>(brokerages, HttpStatus.OK);
	}

	// -------------------Retrieve Single Brokerage------------------------------------------
	@Secured({  "ROLE_ADMIN","ROLE_MANAGER" })
	@RequestMapping(value = "/brokerage/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBrokerage(@PathVariable("id") int id) {
		logger.info("Fetching Brokerage with id {}", id);
		Brokerage brokerage = brokerageService.findById(id);
		if (brokerage == null) {
			logger.error("Brokerage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Brokerage with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Brokerage>(brokerage, HttpStatus.OK);
	}

	// -------------------Create a Brokerage-------------------------------------------
	@Secured({  "ROLE_ADMIN","ROLE_MANAGER" })
	@RequestMapping(value = "/brokerage/", method = RequestMethod.POST)
	public ResponseEntity<?> createBrokerage(@RequestBody Brokerage brokerage, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Brokerage : {}", brokerage);

		if (brokerageService.isBrokerageExist(brokerage)) {
			logger.error("Unable to create. A Brokerage with name {} already exist", brokerage.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Brokerage with name " + 
			brokerage.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		brokerage.setCreatedBy("sarath");
		brokerage.setUpdatedBy("sarath");
		brokerageService.saveBrokerage(brokerage);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/brokerage/{id}").buildAndExpand(brokerage.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Brokerage ------------------------------------------------
	@Secured({  "ROLE_ADMIN","ROLE_MANAGER" })
	@RequestMapping(value = "/brokerage/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBrokerage(@PathVariable("id") int id, @RequestBody Brokerage brokerage) {
		logger.info("Updating Brokerage with id {}", id);

		Brokerage currentBrokerage = brokerageService.findById(id);

		if (currentBrokerage == null) {
			logger.error("Unable to update. Brokerage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Brokerage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentBrokerage.setId(brokerage.getId());
		currentBrokerage.setDescription(brokerage.getDescription());

		brokerageService.updateBrokerage(currentBrokerage);
		return new ResponseEntity<Brokerage>(currentBrokerage, HttpStatus.OK);
	}

	// ------------------- Delete a Brokerage-----------------------------------------
	@Secured({  "ROLE_ADMIN" })
	@RequestMapping(value = "/brokerage/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBrokerage(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting Brokerage with id {}", id);

		Brokerage brokerage = brokerageService.findById(id);
		if (brokerage == null) {
			logger.error("Unable to delete. Brokerage with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Brokerage with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		brokerageService.deleteBrokerageById(id);
		return new ResponseEntity<Brokerage>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Brokerages-----------------------------
	@Secured({  "ROLE_ADMIN" })
	@RequestMapping(value = "/brokerage/", method = RequestMethod.DELETE)
	public ResponseEntity<Brokerage> deleteAllBrokerages() {
		logger.info("Deleting All Brokerages");

		brokerageService.deleteAllBrokerages();
		return new ResponseEntity<Brokerage>(HttpStatus.NO_CONTENT);
	}

}