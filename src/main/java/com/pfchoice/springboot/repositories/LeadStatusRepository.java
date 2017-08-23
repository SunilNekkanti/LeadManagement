package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadStatus;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatus, Short> {

	public LeadStatus findById(Short id);
	
	public LeadStatus findByDescription(String description);

}

