package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.LeadNotes;

public interface LeadNotesService {
	
	LeadNotes findById(Integer id);

	LeadNotes findByNotes(String notes);

	void saveLeadNotes(LeadNotes leadNotes);

	void updateLeadNotes(LeadNotes leadNotes);

	void deleteLeadNotesById(Integer id);

	void deleteAllLeadNotes();

	List<LeadNotes> findAllLeadNotes();

	boolean isLeadNotesExist(LeadNotes leadNotes);
}