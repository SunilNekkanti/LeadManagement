package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    public Insurance findById(Integer id );
	
	public Insurance findByName(String name);
}

