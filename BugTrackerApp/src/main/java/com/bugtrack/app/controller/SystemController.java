package com.bugtrack.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/System")
public class SystemController  extends CommonController {
	
	/**
	 * the main view of assign bugs
	 * @return template of /Bug/assign
	 */
	@RequestMapping(value="products", method=RequestMethod.GET)
	public ModelAndView index01(){
		ModelAndView mv = new ModelAndView("System/products");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
	
	@RequestMapping(value="bugparams", method=RequestMethod.GET)
	public ModelAndView index02(){
		ModelAndView mv = new ModelAndView("System/bugparams");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
	
	//
	@RequestMapping(value="parameters", method=RequestMethod.GET)
	public ModelAndView index03(){
		ModelAndView mv = new ModelAndView("System/parameters");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
