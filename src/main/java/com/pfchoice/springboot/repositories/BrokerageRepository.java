package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Brokerage;

@Repository
public interface BrokerageRepository extends JpaRepository<Brokerage, Integer> {

	public Brokerage findById(Integer id);
	
	public Brokerage findByDescription(String code);
}

