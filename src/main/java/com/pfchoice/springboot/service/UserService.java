package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.User;

public interface UserService {
	
	User findById(Long id);

	User findByUsername(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);
}