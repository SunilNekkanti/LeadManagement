package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.Provider;

public interface ProviderService {
	
	Provider findById(Integer id);

	Provider findByName(String name);

	void saveProvider(Provider provider);

	void updateProvider(Provider provider);

	void deleteProviderById(Integer id);

	void deleteAllProviders();

	List<Provider> findAllProviders();

	boolean isProviderExist(Provider provider);
}