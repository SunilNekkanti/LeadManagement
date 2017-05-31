package com.pfchoice.springboot.service.impl;

import java.util.Date;

import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.repositories.LeadMembershipRepository;
import com.pfchoice.springboot.service.LeadMembershipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<LeadMembership> findAllLeadMembershipsByPage(Pageable pageable){
		return leadMembershipRepository.findAll(pageable);
	}
	
	public boolean isLeadMembershipExists(String leadFirstName, String leadLastName, Date dob) {
		return !leadMembershipRepository.findLeadMembershipByLastNameFirstNameDob( leadFirstName, leadLastName, dob ).isEmpty() ;
	}

}
