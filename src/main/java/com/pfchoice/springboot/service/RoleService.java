package com.pfchoice.springboot.service;


import java.util.List;

import com.pfchoice.springboot.model.Role;

public interface RoleService {
	
	Role findById(Integer id);

	Role findByRole(String name);

	void saveRole(Role role);

	void updateRole(Role role);

	void deleteRoleById(Integer id);

	void deleteAllRoles();

	List<Role> findAllRoles();

	boolean isRoleExist(Role role);
	
	List<Role> findDistinctRoles();
	
}