package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.ActivityType;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {

	public ActivityType findById(Integer id);
	
	public ActivityType findByDescription(String code);
}

