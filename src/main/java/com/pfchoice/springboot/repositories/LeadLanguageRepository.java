package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadLanguage;

@Repository
public interface LeadLanguageRepository extends JpaRepository<LeadLanguage, Byte> {

	public LeadLanguage findById(Byte id);
	
	public LeadLanguage findByDescription(String description);

}

