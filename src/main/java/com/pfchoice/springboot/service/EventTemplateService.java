package com.pfchoice.springboot.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.EventTemplate;

public interface EventTemplateService {
	
	EventTemplate findById(Integer id);
	
	void saveEventTemplate(EventTemplate eventTemplate);

	void updateEventTemplate(EventTemplate eventTemplate);

	void deleteEventTemplateById(Integer id);

	void deleteAllEventTemplates();

	Page<EventTemplate> findAllEventTemplatesByPage(Specification<EventTemplate> spec,Pageable pageable);

	boolean isEventTemplateExists(String eventTemplateName);
}