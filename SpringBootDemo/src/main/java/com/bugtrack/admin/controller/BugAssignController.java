package com.bugtrack.admin.controller;

import java.util.HashMap;
import java.util.List;
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

import com.bugtrack.admin.dao.BugClassRepository;
import com.bugtrack.admin.dao.BugPriorityRepository;
import com.bugtrack.admin.dao.BugStatusRepository;
import com.bugtrack.admin.model.BugClassModel;
import com.bugtrack.admin.model.BugPriorityModel;
import com.bugtrack.admin.model.BugStatusModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Bug")
public class BugAssignController extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * the main view of assign bugs
	 * 
	 * @return template of /Bug/assign
	 */
	@RequestMapping(value = "assign", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/Bug/assign");
		mv.addObject("fullname", this.getFullname());
		return mv;
	}

	@Autowired
	BugStatusRepository bugstatusRepo;

	@RequestMapping(value = "bugstatus", method = RequestMethod.GET)
	public BugStatusModel getStatusById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return bugstatusRepo.findOne(id);
	}

	@RequestMapping(value = "bugstatuslist", method = RequestMethod.GET)
	public List<BugStatusModel> getBugStatusList(HttpServletRequest request) {
		return bugstatusRepo.findAll();
	}

	@Autowired
	BugClassRepository bugclassRepo;

	@RequestMapping(value = "bugclasslist", method = RequestMethod.GET)
	public List<BugClassModel> getBugClassList(HttpServletRequest request) {
		return bugclassRepo.findAll();
	}

	@Autowired
	BugPriorityRepository bugpriorityRepo;

	@RequestMapping(value = "bugprioritylist", method = RequestMethod.GET)
	public List<BugPriorityModel> getBugPriorityList(HttpServletRequest request) {
		return bugpriorityRepo.findAll();
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Map<String, Object> bugList(HttpServletRequest request, PageContent page,
			@RequestParam(value = "pname", required = false) String pname,
			@RequestParam(value = "pver", required = false) String pver,
			@RequestParam(value = "pos", required = false) String pos,
			@RequestParam(value = "bstatus", required = false) String bstatus,
			@RequestParam(value = "btitle", required = false) String btitle,
			@RequestParam(value = "bstart", required = false) String bstart,
			@RequestParam(value = "bend", required = false) String bend,
			@RequestParam(value = "bpriority", required = false) String bpriority,
			@RequestParam(value = "bcategory", required = false) String bcategory,
			@RequestParam(value = "bid", required = false) String bid) throws JsonProcessingException {
		// keyword
		Map<String, String> keywords = new HashMap<String, String>();
		if (pname != null && !pname.trim().equals(""))
			keywords.put("pname", pname);
		if (pver != null && !pver.trim().equals(""))
			keywords.put("pver", pver);
		if (pos != null && !pos.trim().equals(""))
			keywords.put("pos", pos);
		if (bstatus != null && !bstatus.trim().equals(""))
			keywords.put("bstatus", bstatus);
		if (btitle != null && !btitle.trim().equals(""))
			keywords.put("btitle", btitle);
		if (bstart != null && !bstart.trim().equals(""))
			keywords.put("bstart", bstart);
		if (bend != null && !bend.trim().equals(""))
			keywords.put("bend", bend);
		if (bpriority != null && !bpriority.trim().equals(""))
			keywords.put("bpriority", bpriority);
		if (bcategory != null && !bcategory.trim().equals(""))
			keywords.put("bcategory", bcategory);
		if (bid != null && !bid.trim().equals(""))
			keywords.put("bid", bid);

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
		Map<String, Object> map = bugRepo.findAll(keywords, pageable);

		// debug
		logger.debug("------------------ /Bug/list");
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		logger.debug(jsonString);

		return map;
	}
}
