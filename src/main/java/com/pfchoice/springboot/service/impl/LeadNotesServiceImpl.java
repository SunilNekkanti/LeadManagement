package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.LeadNotes;
import com.pfchoice.springboot.repositories.LeadNotesRepository;
import com.pfchoice.springboot.service.LeadNotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("leadNotesService")
@Transactional
public class LeadNotesServiceImpl implements LeadNotesService{

	@Autowired
	private LeadNotesRepository brokerageRepository;

	public LeadNotes findById(Integer id) {
		return brokerageRepository.findOne(id);
	}


	public LeadNotes findByNotes(String notes) {
		return brokerageRepository.findByNotes(notes);
	}

	public void saveLeadNotes(LeadNotes brokerage) {
		brokerageRepository.save(brokerage);
	}

	public void updateLeadNotes(LeadNotes brokerage){
		saveLeadNotes(brokerage);
	}

	public void deleteLeadNotesById(Integer id){
		brokerageRepository.delete(id);
	}

	public void deleteAllLeadNotes(){
		brokerageRepository.deleteAll();
	}

	public List<LeadNotes> findAllLeadNotes(){
		return brokerageRepository.findAll();
	}

	public boolean isLeadNotesExist(LeadNotes leadNotes) {
		return findByNotes(leadNotes.getNotes()) != null;
	}

}
