package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Byte> {

	public Language findById(Byte id);
	
	public Language findByDescription(String description);

}

