package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Bug")
public class BugProcessController  extends CommonController {
	
	/**
	 * the main view of process bugs
	 * @return template of /Bug/process
	 */
	@RequestMapping(value="process", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("/Bug/process");
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
