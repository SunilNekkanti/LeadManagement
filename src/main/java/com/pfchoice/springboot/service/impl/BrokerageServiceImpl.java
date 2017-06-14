package com.pfchoice.springboot.service.impl;

import java.util.List;

import com.pfchoice.springboot.model.Brokerage;
import com.pfchoice.springboot.repositories.BrokerageRepository;
import com.pfchoice.springboot.service.BrokerageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("brokerageService")
@Transactional
public class BrokerageServiceImpl implements BrokerageService{

	@Autowired
	private BrokerageRepository brokerageRepository;

	public Brokerage findById(Integer id) {
		return brokerageRepository.findOne(id);
	}


	public Brokerage findByDescription(String name) {
		return brokerageRepository.findByDescription(name);
	}

	public void saveBrokerage(Brokerage brokerage) {
		brokerageRepository.save(brokerage);
	}

	public void updateBrokerage(Brokerage brokerage){
		saveBrokerage(brokerage);
	}

	public void deleteBrokerageById(Integer id){
		brokerageRepository.delete(id);
	}

	public void deleteAllBrokerages(){
		brokerageRepository.deleteAll();
	}

	public List<Brokerage> findAllBrokerages(){
		return brokerageRepository.findAll();
	}

	public boolean isBrokerageExist(Brokerage brokerage) {
		return findByDescription(brokerage.getDescription()) != null;
	}

}
