package com.pfchoice.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.FileUpload;
import com.pfchoice.springboot.service.FileUploadService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class FileUploadController {

	public static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	public static String FILES_UPLOAD_DIRECTORY_PATH = "c:/softwares/";

	@Autowired
	FileUploadService fileUploadService; // Service which will do all data
											// retrieval/manipulation work

	// -------------------Retrieve All
	// FileUploads---------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUpload/", method = RequestMethod.GET)
	public ResponseEntity<List<FileUpload>> listAllFileUploads() {

		List<FileUpload> fileUploads = fileUploadService.findAllFileUploads();
		if (fileUploads.isEmpty()) {
			logger.info("no fileUploads");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<FileUpload>>(fileUploads, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// FileUpload------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUpload/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getFileUpload(@PathVariable("id") int id) {
		logger.info("Fetching FileUpload with id {}", id);
		FileUpload fileUpload = fileUploadService.findById(id);
		if (fileUpload == null) {
			logger.error("FileUpload with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("FileUpload with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FileUpload>(fileUpload, HttpStatus.OK);
	}

	// -------------------Create a
	// FileUpload-------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/fileUpload/", method = RequestMethod.POST)
	public ResponseEntity<?> createFileUpload(@RequestBody FileUpload fileUpload, 
			UriComponentsBuilder ucBuilder,@ModelAttribute("username") String username) {
		logger.info("Creating FileUpload : {}", fileUpload);

		if (fileUploadService.isFileUploadExists(fileUpload)) {
			logger.error("Unable to create. A FileUpload with name {} already exist", fileUpload.getFileName());
			return new ResponseEntity(
					new CustomErrorType(
							"Unable to create. A FileUpload with name " + fileUpload.getFileName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		fileUpload.setCreatedBy(username);
		fileUpload.setUpdatedBy(username);
		fileUploadService.saveFileUpload(fileUpload);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/fileUpload/{id}").buildAndExpand(fileUpload.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a FileUpload
	// ------------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUpload/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateFileUpload(@PathVariable("id") int id, @RequestBody FileUpload fileUpload) {
		logger.info("Updating FileUpload with id {}", id);

		FileUpload currentFileUpload = fileUploadService.findById(id);

		if (currentFileUpload == null) {
			logger.error("Unable to update. FileUpload with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. FileUpload with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		fileUploadService.updateFileUpload(currentFileUpload);
		return new ResponseEntity<FileUpload>(currentFileUpload, HttpStatus.OK);
	}

	// ------------------- Delete a
	// FileUpload-----------------------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/fileUpload/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFileUpload(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting FileUpload with id {}", id);

		FileUpload fileUpload = fileUploadService.findById(id);
		if (fileUpload == null) {
			logger.error("Unable to delete. FileUpload with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. FileUpload with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		fileUploadService.deleteFileUploadById(id);
		return new ResponseEntity<FileUpload>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All FileUploads-----------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/fileUpload/", method = RequestMethod.DELETE)
	public ResponseEntity<FileUpload> deleteAllFileUploads() {
		logger.info("Deleting All FileUploads");

		fileUploadService.deleteAllFileUploads();
		return new ResponseEntity<FileUpload>(HttpStatus.NO_CONTENT);
	}
 
}