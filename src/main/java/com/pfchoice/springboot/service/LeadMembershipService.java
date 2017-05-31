package com.pfchoice.springboot.service;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pfchoice.springboot.model.LeadMembership;

public interface LeadMembershipService {
	
	LeadMembership findById(Integer id);

	void saveLeadMembership(LeadMembership lead);

	void updateLeadMembership(LeadMembership lead);

	void deleteLeadMembershipById(Integer id);

	void deleteAllLeadMemberships();

	Page<LeadMembership> findAllLeadMembershipsByPage(Pageable pageable);

	boolean isLeadMembershipExists(String leadFirstName, String leadLastName, Date dob);
}