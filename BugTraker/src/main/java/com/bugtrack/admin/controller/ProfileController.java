package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Auth")
public class ProfileController  extends CommonController {
	
	/**
	 * the main view of assign bugs
	 * @return template of /Auth/profile
	 */
	@RequestMapping(value="profile", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("Auth/profile");
		mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
