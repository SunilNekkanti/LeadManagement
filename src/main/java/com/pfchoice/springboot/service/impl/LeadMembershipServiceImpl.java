package com.pfchoice.springboot.service.impl;

import java.util.Date;
import java.util.List;

import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.repositories.LeadMembershipRepository;
import com.pfchoice.springboot.service.LeadMembershipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("leadMembershipService")
@Transactional
public class LeadMembershipServiceImpl implements LeadMembershipService{

	@Autowired
	private LeadMembershipRepository leadMembershipRepository;

	public LeadMembership findById(Integer id) {
		return leadMembershipRepository.findOne(id);
	}

	public void saveLeadMembership(LeadMembership leadMembership) {
		leadMembershipRepository.save(leadMembership);
	}

	public void updateLeadMembership(LeadMembership leadMembership){
		saveLeadMembership(leadMembership);
	}

	public void deleteLeadMembershipById(Integer id){
		leadMembershipRepository.delete(id);
	}

	public void deleteAllLeadMemberships(){
		leadMembershipRepository.deleteAll();
	}

	public List<LeadMembership> findAllLeadMemberships(){
		return leadMembershipRepository.findAll();
	}

	public boolean isLeadMembershipExists(String leadFirstName, String leadLastName, Date dob) {
		return !leadMembershipRepository.findLeadMembershipByLastNameFirstNameDob( leadFirstName, leadLastName, dob ).isEmpty() ;
	}

}
