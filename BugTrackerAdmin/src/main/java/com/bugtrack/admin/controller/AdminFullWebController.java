package com.bugtrack.admin.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class AdminFullWebController extends AdminCommonController {
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		if(this.isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
	
	@RequestMapping(value="Public/login", method=RequestMethod.GET)
	public void defaultLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println(request.getHeader("method"));  
		Enumeration e = request.getHeaderNames();  
		while(e.hasMoreElements()){  
		    String name = (String)e.nextElement();  
		    String value = request.getHeader(name);  
		    System.out.println(name+":"+value);  
		}
		
		String path = "App/login";
		String ref = request.getHeader("referer").trim().toLowerCase();
		if(ref.indexOf("/admin")!=-1){
			path = "Admin/login";
		}else if(ref.indexOf("/app")!=-11){
			path = "App/login";
		}
		response.sendRedirect(path);
	}
}
