package com.pfchoice.springboot.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Event;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {

    public List<Event> findEventByEventName(String eventName);
	
}

