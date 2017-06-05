package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadStatus;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatus, Byte> {

	public LeadStatus findById(Byte id);
	
	public LeadStatus findByDescription(String description);

}

