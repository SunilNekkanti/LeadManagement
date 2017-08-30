package com.pfchoice.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.pfchoice.springboot.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {

	@Autowired
	UserService userService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		List<String> leadHomePageRoles = Arrays.asList("ROLE_ADMIN", "ROLE_AGENT","ROLE_CARE_COORDINATOR");
		
		HttpSession session = httpServletRequest.getSession();
		String redirectUrl = null;
		if (session != null) {
			redirectUrl = (String) session.getAttribute("LAST_PAGE");
		}
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		session.setAttribute("username", authUser.getUsername());
		session.setMaxInactiveInterval(30 * 60);
		// set our response to OK status
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		
		// since we have created our custom success handler, its up to us to
		// where
		// we will redirect the user after successfully login
		if (redirectUrl != null) {
			httpServletResponse.sendRedirect(redirectUrl);
		} else {
			if(authUser.getAuthorities().stream().filter(auth -> leadHomePageRoles.contains(auth.getAuthority())).findAny().isPresent()){
				httpServletResponse.sendRedirect("home#/lead/");
			}else{
				httpServletResponse.sendRedirect("home#/event");
			}
			
		}

	}
}