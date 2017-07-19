package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.ActivityType;
import com.pfchoice.springboot.repositories.ActivityTypeRepository;
import com.pfchoice.springboot.service.ActivityTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("activityTypeService")
@Transactional
public class ActivityTypeServiceImpl implements ActivityTypeService{

	@Autowired
	private ActivityTypeRepository activityTypeRepository;

	public ActivityType findById(Integer id) {
		return activityTypeRepository.findOne(id);
	}


	public ActivityType findByDescription(String name) {
		return activityTypeRepository.findByDescription(name);
	}

	public void saveActivityType(ActivityType activityType) {
		activityTypeRepository.save(activityType);
	}

	public void updateActivityType(ActivityType activityType){
		saveActivityType(activityType);
	}

	public void deleteActivityTypeById(Integer id){
		activityTypeRepository.delete(id);
	}

	public void deleteAllActivityTypes(){
		activityTypeRepository.deleteAll();
	}

	public List<ActivityType> findAllActivityTypes(){
		return activityTypeRepository.findAll();
	}

	public boolean isActivityTypeExist(ActivityType activityType) {
		return findByDescription(activityType.getDescription()) != null;
	}

}
