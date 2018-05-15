package com.pfchoice.springboot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class LeadAccessDeniedHandler implements AccessDeniedHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(LeadAccessDeniedHandler.class);
	
    @Override
    public void handle
      (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) 
      throws IOException, ServletException {
    	logger.info("LeadAccessDeniedHandler ------access denied");
        response.sendRedirect("/403");
    }
}