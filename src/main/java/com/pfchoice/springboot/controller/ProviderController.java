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

import com.pfchoice.springboot.model.Provider;
import com.pfchoice.springboot.service.ProviderService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProviderController {

	public static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

	@Autowired
	ProviderService providerService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Providers---------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN", "ROLE_AGENT"  })
	@RequestMapping(value = "/provider/", method = RequestMethod.GET)
	public ResponseEntity<List<Provider>> listAllProviders() {
		List<Provider> providers = providerService.findAllProviders();
		if (providers.isEmpty()) {
			System.out.println("no providers");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		System.out.println("there are providers");
		return new ResponseEntity<List<Provider>>(providers, HttpStatus.OK);
	}

	// -------------------Retrieve Single Provider------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/provider/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProvider(@PathVariable("id") int id) {
		logger.info("Fetching Provider with id {}", id);
		Provider provider = providerService.findById(id);
		if (provider == null) {
			logger.error("Provider with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Provider with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Provider>(provider, HttpStatus.OK);
	}

	// -------------------Create a Provider-------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/provider/", method = RequestMethod.POST)
	public ResponseEntity<?> createProvider(@RequestBody Provider provider, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Provider : {}", provider);

		if (providerService.isProviderExist(provider)) {
			logger.error("Unable to create. A Provider with name {} already exist", provider.getId());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Provider with name " + 
			provider.getId() + " already exist."),HttpStatus.CONFLICT);
		}
		provider.setCreatedBy("sarath");
		provider.setUpdatedBy("sarath");
		providerService.saveProvider(provider);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/provider/{id}").buildAndExpand(provider.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Provider ------------------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/provider/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProvider(@PathVariable("id") int id, @RequestBody Provider provider) {
		logger.info("Updating Provider with id {}", id);

		Provider currentProvider = providerService.findById(id);

		if (currentProvider == null) {
			logger.error("Unable to update. Provider with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Provider with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentProvider.setName(provider.getName());

		providerService.updateProvider(currentProvider);
		return new ResponseEntity<Provider>(currentProvider, HttpStatus.OK);
	}

	// ------------------- Delete a Provider-----------------------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/provider/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProvider(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting Provider with id {}", id);

		Provider provider = providerService.findById(id);
		if (provider == null) {
			logger.error("Unable to delete. Provider with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Provider with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		providerService.deleteProviderById(id);
		return new ResponseEntity<Provider>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Providers-----------------------------
	@Secured({ "ROLE_SELECTOR", "ROLE_ADMIN" })
	@RequestMapping(value = "/provider/", method = RequestMethod.DELETE)
	public ResponseEntity<Provider> deleteAllProviders() {
		logger.info("Deleting All Providers");

		providerService.deleteAllProviders();
		return new ResponseEntity<Provider>(HttpStatus.NO_CONTENT);
	}

}