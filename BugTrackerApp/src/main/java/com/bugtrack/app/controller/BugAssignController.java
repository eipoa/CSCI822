package com.bugtrack.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Bug")
public class BugAssignController extends CommonController {
	/**
	 * the main view of assign bugs
	 * 
	 * @return template of /Bug/assign
	 */
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("Bug/assign");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
