package com.pfchoice.springboot.controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pfchoice.springboot.model.Role;
import com.pfchoice.springboot.model.User;
import com.pfchoice.springboot.model.LoginForm;
import com.pfchoice.springboot.service.UserService;

@Controller
@SessionAttributes({ "username", "roleId", "userId" , "roleName" })
public class AppController {

	
	@Autowired
	UserService userService;
	
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
	String home(HttpSession session, ModelMap modal, @ModelAttribute("username") String username) {
		
		if (!modal.containsAttribute("username")) {
			modal.addAttribute("username", username);
		}
		User user = userService.findByUsername(username);
		
		LoginForm loginForm = new LoginForm();
		loginForm.setUsername(username);
		if (user!=null && !modal.containsAttribute("userId")) {
			modal.addAttribute("userId", user.getId());
			loginForm.setUserId(user.getId()); 
		}
		Set<Role> roleSet = user.getRoles();
		Iterator<Role> roleSetItr = roleSet.iterator();
		if (roleSetItr.hasNext()) {
			Role role = roleSetItr.next();
			modal.addAttribute("roleId", role.getId());
			modal.addAttribute("roleName", role.getId());
			loginForm.setRoleName(role.getRole()); 
			loginForm.setRoleId(role.getId());
			session.setAttribute("loginUser", loginForm);
		}
		System.out.println("logged in user is "+username);
		return "home";
	}
	

	/**
	 * @param page
	 * @return
	 */
	@RequestMapping("/partials/{page}")
	String partialHandler(ModelMap modal, @PathVariable("page") final String page, @ModelAttribute("username") String username,  @ModelAttribute("roleName") String roleName) {
		modal.addAttribute("roleName", roleName);
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
		System.out.println("inside /accessDenied section/");
		return "403";
	}
	

	@RequestMapping(value = "/getloginInfo", method = RequestMethod.GET)
	@ResponseBody
	public LoginForm getUserInfo(HttpSession session) {
		LoginForm loginUser =  (LoginForm) session.getAttribute("loginUser");
	    return loginUser;
	}

}
