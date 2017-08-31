package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.PlanType;
import com.pfchoice.springboot.repositories.PlanTypeRepository;
import com.pfchoice.springboot.service.PlanTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("planTypeService")
@Transactional
public class PlanTypeServiceImpl implements PlanTypeService {

	@Autowired
	private PlanTypeRepository planTypeRepository;

	public PlanType findById(Integer id) {
		return planTypeRepository.findOne(id);
	}

	public PlanType findByDescription(String name) {
		return planTypeRepository.findByDescription(name);
	}

	public void savePlanType(PlanType planType) {
		planTypeRepository.save(planType);
	}

	public void updatePlanType(PlanType planType) {
		savePlanType(planType);
	}

	public void deletePlanTypeById(Integer id) {
		planTypeRepository.delete(id);
	}

	public void deleteAllPlanTypes() {
		planTypeRepository.deleteAll();
	}

	public List<PlanType> findAllPlanTypes() {
		return planTypeRepository.findAll();
	}

	public boolean isPlanTypeExist(PlanType planType) {
		return findByDescription(planType.getDescription()) != null;
	}

}
