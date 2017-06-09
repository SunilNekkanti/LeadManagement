package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	   public Provider findById(Integer id );
		
	   public Provider findByName(String name);

}

