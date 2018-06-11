package com.pfchoice.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.pfchoice.springboot.model.FileUpload;
import com.pfchoice.springboot.model.FileUploadContent;
import com.pfchoice.springboot.service.FileUploadContentService;
import com.pfchoice.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class FileUploadContentController {

	public static final Logger logger = LoggerFactory.getLogger(FileUploadContentController.class);

	public static String FILES_UPLOAD_DIRECTORY_PATH = "c:/softwares/";

	@Autowired
	FileUploadContentService fileUploadContentService; // Service which will do all data
											// retrieval/manipulation work

	// -------------------Retrieve All
	// FileUploadContents---------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUploadContent/", method = RequestMethod.GET)
	public ResponseEntity<List<FileUploadContent>> listAllFileUploadContents() {

		List<FileUploadContent> fileUploadContents = fileUploadContentService.findAllFileUploadContents();
		if (fileUploadContents.isEmpty()) {
			logger.info("no fileUploadContents");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<FileUploadContent>>(fileUploadContents, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// FileUploadContent------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUploadContent/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getFileUploadContent(@PathVariable("id") int id) {
		logger.info("Fetching FileUploadContent with id {}", id);
		FileUploadContent fileUploadContent = fileUploadContentService.findById(id);
		if (fileUploadContent == null) {
			logger.error("FileUploadContent with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("FileUploadContent with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FileUploadContent>(fileUploadContent, HttpStatus.OK);
	}

	// -------------------Create a
	// FileUploadContent-------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EVENT_COORDINATOR" })
	@RequestMapping(value = "/fileUploadContent/", method = RequestMethod.POST)
	public ResponseEntity<?> createFileUploadContent(@RequestBody FileUploadContent fileUploadContent, 
			UriComponentsBuilder ucBuilder,@ModelAttribute("username") String username) {
		logger.info("Creating FileUploadContent : {}", fileUploadContent);

		if (fileUploadContentService.isFileUploadContentExists(fileUploadContent)) {
			logger.error("Unable to create. A FileUploadContent with name {} already exist", fileUploadContent.getFileName());
			return new ResponseEntity(
					new CustomErrorType(
							"Unable to create. A FileUploadContent with name " + fileUploadContent.getFileName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		logger.info("FileUploadContent username"+username);
		fileUploadContentService.saveFileUploadContent(fileUploadContent);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/fileUploadContent/{id}").buildAndExpand(fileUploadContent.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a FileUploadContent
	// ------------------------------------------------
	@SuppressWarnings("unused")
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER" })
	@RequestMapping(value = "/fileUploadContent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateFileUploadContent(@PathVariable("id") int id, @RequestBody FileUploadContent fileUploadContent
			,@ModelAttribute("username") String username) {
		logger.info("Updating FileUploadContent with id {}", id);

		FileUploadContent currentFileUploadContent = fileUploadContentService.findById(id);
		if (currentFileUploadContent == null) {
			logger.error("Unable to update. FileUploadContent with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. FileUploadContent with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		fileUploadContentService.updateFileUploadContent(currentFileUploadContent);
		return new ResponseEntity<FileUploadContent>(currentFileUploadContent, HttpStatus.OK);
	}

	// ------------------- Delete a
	// FileUploadContent-----------------------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/fileUploadContent/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFileUploadContent(@PathVariable("id") int id) {
		logger.info("Fetching & Deleting FileUploadContent with id {}", id);

		FileUploadContent fileUploadContent = fileUploadContentService.findById(id);
		if (fileUploadContent == null) {
			logger.error("Unable to delete. FileUploadContent with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. FileUploadContent with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		fileUploadContentService.deleteFileUploadContentById(id);
		return new ResponseEntity<FileUploadContent>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All FileUploadContents-----------------------------
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/fileUploadContent/", method = RequestMethod.DELETE)
	public ResponseEntity<FileUploadContent> deleteAllFileUploadContents() {
		logger.info("Deleting All FileUploadContents");

		fileUploadContentService.deleteAllFileUploadContents();
		return new ResponseEntity<FileUploadContent>(HttpStatus.NO_CONTENT);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR" })
	@RequestMapping(value = { "/fileUpload/fileProcessing.do" }, method = RequestMethod.POST)
	public List<FileUpload> uploadFileProcessing(Model model, @RequestParam MultipartFile[] files
			,@ModelAttribute("username") String username) throws IOException {
		logger.info("started file processsing" + files.toString());
		logger.info("fileUploadContent.length:" + files.length);
		List<FileUploadContent> fileUploadContenters = new ArrayList<>();
		List<FileUpload> fileUploaders = new ArrayList<>();

		for (MultipartFile fileUploadContent : files) {
			logger.info("fileUploadContent.getOriginalFilename() :" + fileUploadContent.getOriginalFilename());
			if (fileUploadContent != null && !"".equals(fileUploadContent.getOriginalFilename())) {

				String fileName = fileUploadContent.getOriginalFilename();

				try {
					// String ext = FilenameUtils.getExtension(fileName);
					logger.info("fileName is : " + fileName);
					FileUploadContent fileUploadContenter = new FileUploadContent();
					fileUploadContenter.setFileName(fileName);
					fileUploadContenter.setContentType(fileUploadContent.getContentType());
					fileUploadContenter.setData(fileUploadContent.getBytes());
					fileUploadContentService.saveFileUploadContent(fileUploadContenter);
					
					FileUpload fileupload = new FileUpload();
					fileupload.setId(fileUploadContenter.getId());
					fileupload.setFileName(fileUploadContenter.getFileName());
					fileupload.setContentType(fileUploadContenter.getContentType());
					fileUploaders.add(fileupload);
					fileUploadContenters.add(fileUploadContenter);

				} catch (IOException e) {
					logger.warn(e.getCause().getMessage());
				}

			}
		}
		return fileUploaders;

	}

	// -------------------Retrieve FileUploadContented data
	// ------------------------------------------
	@Secured({ "ROLE_ADMIN", "ROLE_AGENT", "ROLE_MANAGER", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR" })
	@RequestMapping(value = "/fileUploaded/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFileUploadContentContents(@PathVariable("id") int id) {
		FileUploadContent fileUploadContent = fileUploadContentService.findById(id);
		if (fileUploadContent == null) {
			logger.error("FileUploadContent with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("FileUploadContent with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}

		byte[] contents = fileUploadContent.getData();
		HttpHeaders headers = new HttpHeaders();
		String filename = fileUploadContent.getFileName();
		headers.setContentDispositionFormData("inline", filename);
		headers.setContentType(MediaType.parseMediaType(fileUploadContent.getContentType()));

		return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
	}
}