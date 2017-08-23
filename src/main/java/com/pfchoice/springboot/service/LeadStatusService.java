package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.LeadStatus;

public interface LeadStatusService {
	
	LeadStatus findById(Short id);
	
	LeadStatus findByDescription(String description);

	void saveLeadStatus(LeadStatus leadStatus);

	void updateLeadStatus(LeadStatus leadStatus);

	void deleteLeadStatusById(Short id);

	void deleteAllLeadStatuses();

	List<LeadStatus> findAllLeadStatuses();

	boolean isLeadStatusExist(LeadStatus leadStatus);
}