package com.pfchoice.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.LeadMembership;

public interface LeadMembershipService {

	LeadMembership findById(Integer id);

	void saveLeadMembership(LeadMembership lead);

	void updateLeadMembership(LeadMembership lead);

	void deleteLeadMembershipById(Integer id);

	void deleteAllLeadMemberships();

	Page<LeadMembership> findAllLeadMembershipsByPage(Specification<LeadMembership> spec, Pageable pageable);

	boolean isLeadMembershipExists(String leadFirstName, String leadLastName, String address, String phoneNumber);
	
	List<LeadMembership> leadStatusReport(String usrName,String roleIds,String statusIds, String  eventIds, String startDate, String endDate,String  reportType );
}