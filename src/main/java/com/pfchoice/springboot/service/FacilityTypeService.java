package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.FacilityType;

public interface FacilityTypeService {
	
	FacilityType findById(Integer id);

	FacilityType findByDescription(String code);

	void saveFacilityType(FacilityType facilityType);

	void updateFacilityType(FacilityType facilityType);

	void deleteFacilityTypeById(Integer id);

	void deleteAllFacilityTypes();

	List<FacilityType> findAllFacilityTypes();

	boolean isFacilityTypeExist(FacilityType facilityType);
}