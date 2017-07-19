package com.pfchoice.springboot.service.impl;

import com.pfchoice.springboot.model.EventTemplate;
import com.pfchoice.springboot.repositories.EventTemplateRepository;
import com.pfchoice.springboot.service.EventTemplateService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("eventTemplateService")
@Transactional
public class EventTemplateServiceImpl implements EventTemplateService{

	@Autowired
	private EventTemplateRepository eventTemplateRepository;

	public EventTemplate findById(Integer id) {
		return eventTemplateRepository.findOne(id);
	}

	public void saveEventTemplate(EventTemplate eventTemplate) {
		eventTemplateRepository.save(eventTemplate);
	}

	public void updateEventTemplate(EventTemplate eventTemplate){
		saveEventTemplate(eventTemplate);
	}

	public void deleteEventTemplateById(Integer id){
		eventTemplateRepository.delete(id);
	}

	public void deleteAllEventTemplates(){
		eventTemplateRepository.deleteAll();
	}

	public List<EventTemplate> findAllEventTemplates(){
		return (List<EventTemplate>) eventTemplateRepository.findAll();
	}
	
	public Page<EventTemplate> findAllEventTemplatesByPage(Specification<EventTemplate> spec,Pageable pageable){
		return eventTemplateRepository.findAll(spec, pageable);
	}
	
	public boolean isEventTemplateExists(String eventTemplateName) {
		return !eventTemplateRepository.findEventTemplateByName( eventTemplateName).isEmpty() ;
	}

}
