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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.Insurance;
import com.pfchoice.springboot.repositories.specifications.InsuranceSpecifications;
import com.pfchoice.springboot.service.InsuranceService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class InsurnaceController {

	public static final Logger logger = LoggerFactory.getLogger(InsurnaceController.class);

	@Autowired
	InsuranceService insuranceService; // Service which will do all data
										// retrieval/manipulation work

	// -------------------Retrieve All
	// Insurances---------------------------------------------

	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/insurance/", method = RequestMethod.GET)
	public ResponseEntity<?> listAllInsurances(@RequestParam(value = "page", required = false) Integer pageNo,
			@RequestParam(value = "size", required = false) Integer pageSize,
			@RequestParam(value = "search", required = false) String search) {

		pageNo = (pageNo == null) ? 0 : pageNo;
		pageSize = (pageSize == null) ? 1000 : pageSize;

		PageRequest pageRequest = new PageRequest(pageNo, pageSize);
		Specification<Insurance> spec = null;
		if (!"".equals(search))
			spec = new InsuranceSpecifications(search);
		Page<Insurance> insurances = insuranceService.findAllInsurancesByPage(spec, pageRequest);
		if (insurances.getTotalElements() == 0) {
			logger.info("no insurances");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Page<Insurance>>(insurances, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Insurance------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/insurance/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getInsurance(@PathVariable("id") int id) {
		logger.info("Fetching Insurance with id {}", id);
		Insurance insurance = insuranceService.findById(id);
		if (insurance == null) {
			logger.error("Insurance with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Insurance with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Insurance>(insurance, HttpStatus.OK);
	}

	// -------------------Create a
	// Insurance-------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/insurance/", method = RequestMethod.POST)
	public ResponseEntity<?> createInsurance(@RequestBody Insurance insurance, 
			UriComponentsBuilder ucBuilder,@ModelAttribute("username") String username) {
		logger.info("Creating Insurance : {}", insurance);

		if (insuranceService.isInsuranceExist(insurance)) {
			logger.error("Unable to create. A Insurance with name {} already exist", insurance.getId());
			return new ResponseEntity(
					new CustomErrorType(
							"Unable to create. A Insurance with name " + insurance.getId() + " already exist."),
					HttpStatus.CONFLICT);
		}
		insuranceService.saveInsurance(insurance);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/insurance/{id}").buildAndExpand(insurance.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Insurance
	// ------------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/insurance/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateInsurance(@PathVariable("id") int id, @RequestBody Insurance insurance) {
		logger.info("Updating Insurance with id {}", id);

		Insurance currentInsurance = insuranceService.findById(id);

		if (currentInsurance == null) {
			logger.error("Unable to update. Insurance with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Insurance with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentInsurance.setName(insurance.getName());

		insuranceService.updateInsurance(currentInsurance);
		return new ResponseEntity<Insurance>(currentInsurance, HttpStatus.OK);
	}

	// ------------------- Delete a
	// Insurance-----------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER","ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/insurance/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteInsurance(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting Insurance with id {}", id);

		Insurance insurance = insuranceService.findById(id);
		if (insurance == null) {
			logger.error("Unable to delete. Insurance with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Insurance with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		insuranceService.deleteInsuranceById(id);
		return new ResponseEntity<Insurance>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Insurances-----------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/insurance/", method = RequestMethod.DELETE)
	public ResponseEntity<Insurance> deleteAllInsurances() {
		logger.info("Deleting All Insurances");

		insuranceService.deleteAllInsurances();
		return new ResponseEntity<Insurance>(HttpStatus.NO_CONTENT);
	}

}