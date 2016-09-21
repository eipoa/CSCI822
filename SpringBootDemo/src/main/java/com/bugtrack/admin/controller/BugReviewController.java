package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/Bug")
public class BugReviewController  extends CommonController {
	
	/**
	 * the main view of review bugs
	 * @return template of /Bug/review
	 */
	@RequestMapping(value="review", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("/Bug/review");
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
