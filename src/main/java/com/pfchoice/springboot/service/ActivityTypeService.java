package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.ActivityType;

public interface ActivityTypeService {
	
	ActivityType findById(Integer id);

	ActivityType findByDescription(String code);

	void saveActivityType(ActivityType activityType);

	void updateActivityType(ActivityType activityType);

	void deleteActivityTypeById(Integer id);

	void deleteAllActivityTypes();

	List<ActivityType> findAllActivityTypes();

	boolean isActivityTypeExist(ActivityType activityType);
}