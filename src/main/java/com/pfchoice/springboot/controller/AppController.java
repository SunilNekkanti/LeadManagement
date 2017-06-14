package com.pfchoice.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pfchoice.springboot.model.LoginForm;

@Controller
public class AppController {

	/**
	 * @param modal
	 * @return
	 */
	@RequestMapping(value={"/","/login"})
	String login(ModelMap modal) {
		modal.addAttribute("title","CRUD Example");
		System.out.println("inside app");
		LoginForm loginForm = new LoginForm();
		modal.addAttribute("loginForm", loginForm);
		return "login";
	}
	
	/**
	 * @param modal
	 * @return
	 */
	@RequestMapping("/home")
	String home(ModelMap modal) {
		return "home";
	}
	

	/**
	 * @param page
	 * @return
	 */
	@RequestMapping("/partials/{page}")
	String partialHandler(@PathVariable("page") final String page) {
		System.out.println("inside /partials/{page} /partials/"+page);
		return page;
	}
	

	/**
	 * for 403 access denied page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() {

		return "403";
	}
	

}
