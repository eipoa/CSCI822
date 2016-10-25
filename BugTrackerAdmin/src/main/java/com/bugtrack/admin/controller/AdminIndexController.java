package com.bugtrack.admin.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		
		List<Object[]> abc01 = bugRepo.findReport01();
		List<Map<String, Object>> rep01 = new ArrayList<Map<String, Object>>();
		List<Object[]> abc02 = bugRepo.findReport04();
		for(int i = 0; i < abc01.size(); i++){
			Map<String, Object> repTmp = new HashMap<String, Object>();
			repTmp.put("name", abc01.get(i)[0].toString());
			repTmp.put("value1",  Integer.parseInt(abc01.get(i)[1].toString()));
			repTmp.put("value2",  Integer.parseInt(abc02.get(i)[1].toString()));
			//System.out.println(repTmp.toString());
			rep01.add(repTmp);
		}
		mv.addObject("rep01", rep01);

//		List<Object[]> abc02 = bugRepo.findReport02();
//		List<Map<String, Object>> rep02 = new ArrayList<Map<String, Object>>();
//		for(int i = 0; i < abc02.size(); i++){
//			Map<String, Object> repTmp = new HashMap<String, Object>();
//			repTmp.put("name", abc02.get(i)[0].toString());
//			repTmp.put("value",  Integer.parseInt(abc02.get(i)[1].toString()));
//			//System.out.println(repTmp.toString());
//			rep02.add(repTmp);
//		}
//		mv.addObject("rep02", rep02);
		
		List<Object[]> abc03 = bugRepo.findReport03();
		List<Map<String, Object>> rep03 = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < abc03.size(); i++){
			Map<String, Object> repTmp = new HashMap<String, Object>();
			repTmp.put("name", abc03.get(i)[0].toString());
			repTmp.put("value",  Integer.parseInt(abc03.get(i)[1].toString()));
			//System.out.println(repTmp.toString());
			rep03.add(repTmp);
		}
		mv.addObject("rep03", rep03);
		
		// tasks
		Integer allTasks = bugRepo.findReportAllTask();
		mv.addObject("alltask", allTasks);
		Integer allsTasks = bugRepo.findReportAllSTask();
		mv.addObject("allstask", allsTasks);

		DateFormat df = new SimpleDateFormat("MMM",Locale.ENGLISH);
        System.out.println(df.format(new Date()));
        mv.addObject("mon", df.format(new Date()));

		return mv;
	}
}
