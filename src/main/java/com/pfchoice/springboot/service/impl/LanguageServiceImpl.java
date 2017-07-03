package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.Language;
import com.pfchoice.springboot.repositories.LanguageRepository;
import com.pfchoice.springboot.service.LanguageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("LanguageService")
@Transactional
public class LanguageServiceImpl implements LanguageService{

	@Autowired
	private LanguageRepository LanguageRepository;


	public Language findById(Byte id) {
		return LanguageRepository.findById(id);
	}

	public Language findByDescription(String description) {
		return LanguageRepository.findByDescription(description);
	}

	public void saveLanguage(Language Language) {
		LanguageRepository.save(Language);
	}

	public void updateLanguage(Language Language){
		saveLanguage(Language);
	}

	public void deleteLanguageById(Byte id){
		LanguageRepository.delete(id);
	}

	public void deleteAllLanguages(){
		LanguageRepository.deleteAll();
	}

	public List<Language> findAllLanguages(){
		return LanguageRepository.findAll();
	}

	public boolean isLanguageExist(Language Language) {
		return findByDescription(Language.getDescription()) != null;
	}

}
