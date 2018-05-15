package com.pfchoice.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class LeadAccessDeniedHandler implements AccessDeniedHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(LeadAccessDeniedHandler.class);
	
	
    @Override
    public void handle
      (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) 
      throws IOException, ServletException {
    	logger.info("LeadAccessDeniedHandler ------access denied");
    	  Authentication auth 
          = SecurityContextHolder.getContext().getAuthentication();
    	  if (auth != null) {
        	logger.info("User: " + auth.getName() 
              + " attempted to access the protected URL: "
              + request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() +"/accessDenied");
    }
}