package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.LeadLanguage;
import com.pfchoice.springboot.repositories.LeadLanguageRepository;
import com.pfchoice.springboot.service.LeadLanguageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("leadLanguageService")
@Transactional
public class LeadLanguageServiceImpl implements LeadLanguageService{

	@Autowired
	private LeadLanguageRepository leadLanguageRepository;


	public LeadLanguage findById(Byte id) {
		return leadLanguageRepository.findById(id);
	}

	public LeadLanguage findByDescription(String description) {
		return leadLanguageRepository.findByDescription(description);
	}

	public void saveLeadLanguage(LeadLanguage leadLanguage) {
		leadLanguageRepository.save(leadLanguage);
	}

	public void updateLeadLanguage(LeadLanguage leadLanguage){
		saveLeadLanguage(leadLanguage);
	}

	public void deleteLeadLanguageById(Byte id){
		leadLanguageRepository.delete(id);
	}

	public void deleteAllLeadLanguages(){
		leadLanguageRepository.deleteAll();
	}

	public List<LeadLanguage> findAllLeadLanguages(){
		return leadLanguageRepository.findAll();
	}

	public boolean isLeadLanguageExist(LeadLanguage leadLanguage) {
		return findByDescription(leadLanguage.getDescription()) != null;
	}

}
