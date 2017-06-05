package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.Insurance;

public interface InsuranceService {
	
	Insurance findById(Integer id);

	Insurance findByName(String name);

	void saveInsurance(Insurance insurance);

	void updateInsurance(Insurance insurance);

	void deleteInsuranceById(Integer id);

	void deleteAllInsurances();

	List<Insurance> findAllInsurances();

	boolean isInsuranceExist(Insurance insurance);
}