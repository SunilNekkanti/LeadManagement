package com.pfchoice.springboot.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.service.EmailService;
import com.pfchoice.springboot.service.EventService;
import com.pfchoice.springboot.service.LeadMembershipService;
import com.pfchoice.springboot.service.UserService;

@RestController
@RequestMapping("/api")
@SuppressWarnings({ "unchecked", "rawtypes" })
@SessionAttributes({ "username", "roleId", "userId", "roleName" })
public class ReportsController {

	public static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

	@Autowired
	EventService eventService; // Service which will do all data
								// retrieval/manipulation work

	@Autowired
	UserService userService; // Service which will do all data//
								// retrieval/manipulation work
	
	@Autowired
	LeadMembershipService leadMbrService; // Service which will do all data//
								// retrieval/manipulation work

	@Autowired
	EmailService emailService;
	// -------------------Retrieve All
	// Events---------------------------------------------

	@Secured({ "ROLE_ADMIN", "ROLE_EVENT_COORDINATOR", "ROLE_CARE_COORDINATOR", "ROLE_MANAGER" })
	@RequestMapping(value = "/statusReport/", method = RequestMethod.GET)
	public ResponseEntity<List<LeadMembership>> statusReport(@PageableDefault(page=0 ,size=100) Pageable pageRequest,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "userName", required = true) String usrName,
			@RequestParam(value = "roleIds", required = true) String roleIds,
			@RequestParam(value = "statusIds", required = true) String statusIds,
			@RequestParam(value = "eventIds", required = true) String eventIds,
			@RequestParam(value = "startDate", required = true)   Date startDate,
			@RequestParam(value = "endDate", required = true)  Date endDate,
			@RequestParam(value = "reportType", required = false) String reportType,
			@ModelAttribute("userId") Integer userId,
			@ModelAttribute("roleName") String roleName) {

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String starttDate = formatter.format(startDate);
		String enddDate = formatter.format(endDate);
		List<LeadMembership> reportData = leadMbrService.leadStatusReport(usrName,roleIds,statusIds, eventIds,starttDate,enddDate, reportType );

		if (reportData.size() == 0) {
			logger.info("no data");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LeadMembership>>(reportData, HttpStatus.OK);
	}

}