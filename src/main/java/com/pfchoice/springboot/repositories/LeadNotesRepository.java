package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadNotes;

@Repository
public interface LeadNotesRepository extends JpaRepository<LeadNotes, Integer> {

	public LeadNotes findById(Integer id);
	
	public LeadNotes findByUserId(Integer userId);
	
	public LeadNotes findByNotes(String notes);
}

