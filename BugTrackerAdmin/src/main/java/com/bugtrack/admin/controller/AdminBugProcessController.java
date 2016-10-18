package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;

@RestController
@RequestMapping("/Admin/Bug")
public class AdminBugProcessController  extends CommonController {
	
	/**
	 * the main view of process bugs
	 * @return template of /Bug/process
	 */
	@RequestMapping(value="process", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("Admin/Bug/process");
		if(isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
}
