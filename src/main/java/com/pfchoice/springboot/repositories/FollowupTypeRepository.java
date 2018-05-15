package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.FollowupType;
import com.pfchoice.springboot.repositories.intf.RecordDetailsAwareRepository;

@Repository
public interface FollowupTypeRepository extends JpaRepository<FollowupType, Long> 
, RecordDetailsAwareRepository<FollowupType, Long>  {

}
