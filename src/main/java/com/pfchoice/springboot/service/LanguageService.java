package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.Language;

public interface LanguageService {
	
	Language findById(Byte id);
	
	Language findByDescription(String description);

	void saveLanguage(Language language);

	void updateLanguage(Language language);

	void deleteLanguageById(Byte id);

	void deleteAllLanguages();

	List<Language> findAllLanguages();

	boolean isLanguageExist(Language language);
}