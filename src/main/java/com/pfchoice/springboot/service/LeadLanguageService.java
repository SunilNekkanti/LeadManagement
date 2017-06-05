package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.LeadLanguage;

public interface LeadLanguageService {
	
	LeadLanguage findById(Byte id);
	
	LeadLanguage findByDescription(String description);

	void saveLeadLanguage(LeadLanguage language);

	void updateLeadLanguage(LeadLanguage language);

	void deleteLeadLanguageById(Byte id);

	void deleteAllLeadLanguages();

	List<LeadLanguage> findAllLeadLanguages();

	boolean isLeadLanguageExist(LeadLanguage language);
}