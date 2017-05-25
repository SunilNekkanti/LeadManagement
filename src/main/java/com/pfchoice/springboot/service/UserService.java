package com.pfchoice.springboot.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pfchoice.springboot.model.User;

public interface UserService {
	
	User findById(Integer id);

	User findByUsername(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Integer id);

	void deleteAllUsers();

	Page<User> findAllUsersByPage(Pageable pageable);

	boolean isUserExist(User user);
	
}