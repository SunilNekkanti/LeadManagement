package com.pfchoice.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfchoice.springboot.model.Role;
import com.pfchoice.springboot.repositories.UserRepository;

/**
 *
 * @author Sarath
 */

@Service
@Transactional
@Primary
public class AuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.pfchoice.springboot.model.User user = userRepository.findByUsername(username);
		Set<Role> roles = user.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role r : roles) {
			GrantedAuthority authority = new SimpleGrantedAuthority(r.getRole());
			authorities.add(authority);
		}

		return new User(user.getUsername(), user.getPassword(), authorities);

	}
}
