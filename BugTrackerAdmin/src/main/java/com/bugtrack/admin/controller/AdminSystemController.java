package com.bugtrack.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugSeverityModel;
import com.bugtrack.model.SysNoteModel;

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
	
	@RequestMapping(value = "savepara")
	public String saveParameter(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "desc", required = false) String desc,
			@RequestParam(value = "ty", required = false) Integer ty) throws Exception {
		if(ty.equals(1)){
			BugPriorityModel obj = new BugPriorityModel();
			if(id!=null){
				obj.setId(id);
			}
			obj.setDescp(desc);
			obj = bugpriorityRepo.saveAndFlush(obj);
		}else if(ty.equals(2)){
			BugClassModel obj = new BugClassModel();
			if(id!=null){
				obj.setId(id);
			}
			obj.setDescp(desc);
			obj = bugclassRepo.saveAndFlush(obj);
		}else if(ty.equals(3)){
			BugSeverityModel obj = new BugSeverityModel();
			if(id!=null){
				obj.setId(id);
			}
			obj.setSeverity(desc);
			obj = sevRepo.saveAndFlush(obj);
		}else
			throw new Exception("Invalid parameter");
		
		return ajaxReturn(true, "", "OK");
	}
	
	@RequestMapping(value = "savenote", method = RequestMethod.POST)
	public String saveParameter(SysNoteModel note) throws Exception {
		note = sysnRepo.saveAndFlush(note);
		return ajaxReturn(true, note.getId().toString(), "OK");
	}
}
