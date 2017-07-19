package com.pfchoice.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Event;
import com.pfchoice.springboot.model.EventTemplate;

@Repository
public interface EventTemplateRepository extends PagingAndSortingRepository<EventTemplate, Integer>, JpaSpecificationExecutor<EventTemplate>  {

    public List<Event> findEventTemplateByName(String templateName);
	
}

