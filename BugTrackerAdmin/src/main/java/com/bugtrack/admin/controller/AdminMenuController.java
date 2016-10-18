/**
 * 
 */
package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Admin/Auth")
public class AdminMenuController extends CommonController {
	@RequestMapping(value = "menus", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Admin/Auth/menus");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
}
