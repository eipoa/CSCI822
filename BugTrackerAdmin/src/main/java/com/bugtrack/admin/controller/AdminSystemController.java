package com.bugtrack.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugStatusModel;

@RestController
@RequestMapping("/Admin/System")
public class AdminSystemController  extends CommonController {
	
	/**
	 * the main view of assign bugs
	 * @return template of /Bug/assign
	 */
	@RequestMapping(value="products", method=RequestMethod.GET)
	public ModelAndView index01(){
		ModelAndView mv = new ModelAndView("Admin/System/products");
		if(isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
	
	@RequestMapping(value="bugparams", method=RequestMethod.GET)
	public ModelAndView index02(){
		ModelAndView mv = new ModelAndView("Admin/System/bugparams");
		if(isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
	
	//
	@RequestMapping(value="parameters", method=RequestMethod.GET)
	public ModelAndView index03(){
		ModelAndView mv = new ModelAndView("Admin/System/parameters");
		if(isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
	
	@RequestMapping(value = "savepara", method = RequestMethod.POST)
	public String saveParameter(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "ty", required = true) Integer ty) throws Exception {
		if(ty.equals(1)){
			BugPriorityModel obj = new BugPriorityModel();
			if(!id.equals(0)){
				obj = bugpriorityRepo.findOne(id);
			}
			obj.setDescp(desc);
			obj = bugpriorityRepo.saveAndFlush(obj);
		}else if(ty.equals(2)){
			BugClassModel obj = new BugClassModel();
			if(!id.equals(0)){
				obj = bugclassRepo.findOne(id);
			}
			obj.setDescp(desc);
			obj = bugclassRepo.saveAndFlush(obj);
		}else
			throw new Exception("Invalid parameter");
		
		return ajaxReturn(true, id.toString(), "OK");
	}
}
