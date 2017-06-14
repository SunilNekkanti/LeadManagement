package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.FacilityType;

@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType, Integer> {

	public FacilityType findById(Integer id);
	
	public FacilityType findByDescription(String code);
}

