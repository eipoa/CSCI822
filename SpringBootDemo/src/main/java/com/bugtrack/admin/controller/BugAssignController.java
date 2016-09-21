package com.bugtrack.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.dao.BugStatusRepository;
import com.bugtrack.admin.model.BugStatusModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Bug")
public class BugAssignController  extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * the main view of assign bugs
	 * @return template of /Bug/assign
	 */
	@RequestMapping(value="assign", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("/Bug/assign");
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
	
	@Autowired
	BugStatusRepository bugstatusRepo;
	@RequestMapping(value = "bugstatus", method = RequestMethod.GET)
	public BugStatusModel getVersionById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return bugstatusRepo.findOne(id);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Map<String, Object> bugList(HttpServletRequest request, PageContent page,
			@RequestParam(value = "id", required = false) String cid,
			@RequestParam(value = "cid", required = false) String pid) throws JsonProcessingException {
		// keyword
		Map<String, String> keywords = new HashMap<String, String>();
		if (cid != null && !cid.trim().equals(""))
			keywords.put("classification_id", cid);
		if (pid != null && !pid.trim().equals(""))
			keywords.put("product_id", pid);

		// page
		Sort sort = null;
		if (page.getOrder().equals("asc")) {
			sort = new Sort(Direction.ASC, page.getSort());
		} else if (page.getOrder().equals("desc")) {
			sort = new Sort(Direction.DESC, page.getSort());
		}
		int pageNum = Integer.parseInt(page.getPage()) - 1;
		int rows = Integer.parseInt(page.getRows());
		Pageable pageable = new PageRequest(pageNum, rows, sort);

		// result for easyui
		/*
		 * // query List<UserModel> list= null; Page<UserModel> list =
		 * repo.findAll(pageable); Map<String, Object> map = new HashMap<String,
		 * Object>(); map.put("total", list.getTotalElements());// count of data
		 * map.put("rows", list.getContent());// data of this page
		 */
		Map<String, Object> map = bugRepo.findAll(keywords, pageable);

		// debug
		logger.debug("------------------ /Bug/list");
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		logger.debug(jsonString);

		return map;
	}
}
