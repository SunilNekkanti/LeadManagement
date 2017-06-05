package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.PlanType;

public interface PlanTypeService {
	
	PlanType findById(Integer id);

	PlanType findByDescription(String code);

	void savePlanType(PlanType planType);

	void updatePlanType(PlanType planType);

	void deletePlanTypeById(Integer id);

	void deleteAllPlanTypes();

	List<PlanType> findAllPlanTypes();

	boolean isPlanTypeExist(PlanType gender);
}