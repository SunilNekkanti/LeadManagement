package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.Brokerage;

public interface BrokerageService {
	
	Brokerage findById(Integer id);

	Brokerage findByDescription(String code);

	void saveBrokerage(Brokerage brokerage);

	void updateBrokerage(Brokerage brokerage);

	void deleteBrokerageById(Integer id);

	void deleteAllBrokerages();

	List<Brokerage> findAllBrokerages();

	boolean isBrokerageExist(Brokerage brokerage);
}