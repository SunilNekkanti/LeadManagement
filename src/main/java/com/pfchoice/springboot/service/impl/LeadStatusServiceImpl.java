package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.LeadStatus;
import com.pfchoice.springboot.repositories.LeadStatusRepository;
import com.pfchoice.springboot.service.LeadStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("leadStatusService")
@Transactional
public class LeadStatusServiceImpl implements LeadStatusService{

	@Autowired
	private LeadStatusRepository leadStatusRepository;


	public LeadStatus findById(Short id) {
		return leadStatusRepository.findById(id);
	}

	public LeadStatus findByDescription(String description) {
		return leadStatusRepository.findByDescription(description);
	}

	public void saveLeadStatus(LeadStatus leadStatus) {
		leadStatusRepository.save(leadStatus);
	}

	public void updateLeadStatus(LeadStatus leadStatus){
		saveLeadStatus(leadStatus);
	}

	public void deleteLeadStatusById(Short id){
		leadStatusRepository.delete(id);
	}

	public void deleteAllLeadStatuses(){
		leadStatusRepository.deleteAll();
	}

	public List<LeadStatus> findAllLeadStatuses(){
		return leadStatusRepository.findAll();
	}

	public boolean isLeadStatusExist(LeadStatus leadStatus) {
		return findByDescription(leadStatus.getDescription()) != null;
	}

}
