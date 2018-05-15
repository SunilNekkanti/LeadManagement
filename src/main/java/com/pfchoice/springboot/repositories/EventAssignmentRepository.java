package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.EventAssignment;
import com.pfchoice.springboot.repositories.intf.RecordDetailsAwareRepository;

@Repository
public interface EventAssignmentRepository
		extends PagingAndSortingRepository<EventAssignment, Integer>, JpaSpecificationExecutor<EventAssignment>
, RecordDetailsAwareRepository<EventAssignment, Integer> {

}
