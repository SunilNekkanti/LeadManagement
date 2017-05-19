package com.pfchoice.springboot.service;


import java.util.Date;
import java.util.List;

import com.pfchoice.springboot.model.LeadMembership;

public interface LeadMembershipService {
	
	LeadMembership findById(Integer id);

	void saveLeadMembership(LeadMembership lead);

	void updateLeadMembership(LeadMembership lead);

	void deleteLeadMembershipById(Integer id);

	void deleteAllLeadMemberships();

	List<LeadMembership> findAllLeadMemberships();

	boolean isLeadMembershipExists(String leadFirstName, String leadLastName, Date dob);
}