package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;

@RestController
@RequestMapping("/Admin/")
public class AdminIndexController extends CommonController {
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("Admin/index");
		if(isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
}
