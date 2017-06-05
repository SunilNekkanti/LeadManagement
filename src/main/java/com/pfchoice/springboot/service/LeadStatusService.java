package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.LeadStatus;

public interface LeadStatusService {
	
	LeadStatus findById(Byte id);
	
	LeadStatus findByDescription(String description);

	void saveLeadStatus(LeadStatus leadStatus);

	void updateLeadStatus(LeadStatus leadStatus);

	void deleteLeadStatusById(Byte id);

	void deleteAllLeadStatuses();

	List<LeadStatus> findAllLeadStatuses();

	boolean isLeadStatusExist(LeadStatus leadStatus);
}