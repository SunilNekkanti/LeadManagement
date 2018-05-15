package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.BestTimeToCall;
import com.pfchoice.springboot.repositories.intf.RecordDetailsAwareRepository;

@Repository
public interface BestTimeToCallRepository extends JpaRepository<BestTimeToCall, Short> 
, RecordDetailsAwareRepository<BestTimeToCall, Short> {

	BestTimeToCall findByDescription(String description);
}
