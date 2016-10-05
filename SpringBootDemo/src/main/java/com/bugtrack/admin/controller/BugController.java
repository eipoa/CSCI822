package com.bugtrack.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.admin.exception.CoustomJsonException;
import com.bugtrack.admin.model.BugClassModel;
import com.bugtrack.admin.model.BugPriorityModel;
import com.bugtrack.admin.model.BugStatusModel;
import com.bugtrack.admin.model.BugsModel;
import com.bugtrack.admin.model.MessageModel;
import com.bugtrack.admin.model.ProductModel;
import com.bugtrack.admin.model.UserModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Bug")
public class BugController extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * the main view of assign bugs
	 * 
	 * @return template of /Bug
	 * @throws CoustomJsonException
	 */
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
			@RequestParam(value = "bid", required = false) String bid,
			@RequestParam(value = "typeid", required = false) String typeid) throws Exception {
		// keyword
		Map<String, String> keywords = new HashMap<String, String>();
		if (pname != null && !pname.trim().equals(""))
			keywords.put("pname", pname);
		if (pver != null && !pver.trim().equals(""))
			keywords.put("pver", pver);
		if (pos != null && !pos.trim().equals(""))
			keywords.put("pos", pos);
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
		if (typeid != null && !typeid.trim().equals("")) {
			if (typeid.trim().equals("1")) {
				keywords.put("triager", Integer.toString(getUserId()));
				// triager can retrive all status
//				if (bstatus != null && !bstatus.trim().equals(""))
//					keywords.put("bstatus", bstatus);
			} else if (typeid.trim().equals("2")) {
				keywords.put("developer", Integer.toString(getUserId()));
				// developer can only retrive assigned status
//				keywords.put("bstatus", "2");
			} else if (typeid.trim().equals("3")) {
				keywords.put("reviewer", Integer.toString(getUserId()));
				// reviewer can only retrive verify status
//				keywords.put("bstatus", "3");
			}
		}
		if (bstatus != null && !bstatus.trim().equals(""))
			keywords.put("bstatus", bstatus);
		logger.debug("------------------ " + typeid);
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

	@Transactional(readOnly = false)
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String modifyBug(HttpServletRequest request, @RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "classification", required = false) String classification,
			@RequestParam(value = "priority_id", required = false) String priority_id,
			@RequestParam(value = "product_name_id", required = false) String product_name_id,
			@RequestParam(value = "product_version", required = false) String product_version,
			@RequestParam(value = "product_os_id", required = false) String product_os_id,
			@RequestParam(value = "status_id", required = false) String status_id,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "short_desc", required = false) String short_desc,
			@RequestParam(value = "developer_id", required = false) String developer_id,
			@RequestParam(value = "reviewer_id", required = false) String reviewer_id,
			@RequestParam(value = "rank", required = false) String rank) throws Exception {
		try {
			BugsModel bug = bugRepo.findById(new Integer(id));
			if (bug == null) {
				throw new CoustomJsonException("cannot find the bug");
			}
			Integer oldstatus = bug.getStatus().getId();
			// status
			BugStatusModel tmpStatus = bugstatusRepo.findOne(new Integer(status_id));
			// classification
			BugClassModel tmpClass = bugclassRepo.findOne(new Integer(classification));
			// priority
			BugPriorityModel tmpPriority = bugpriorityRepo.findOne(new Integer(priority_id));
			// product
			ProductModel tmpProduct = productRepo.findByNameAndVersionAndOs(
					productNameRepo.findOne(new Integer(product_name_id)), product_version,
					productOsRepo.findOne(new Integer(product_os_id)));
			// developer
			UserModel tmpDeveloper = userRepo.findByUsername(developer_id);
			// reviewer
			UserModel tmpReviewer = userRepo.findByUsername(reviewer_id);

			// modifiy bugs
			bug.setRank(new Integer(rank));
			bug.setTitle(title);
			bug.setShort_desc(short_desc);
			bug.setPriority(tmpPriority);
			bug.setStatus(tmpStatus);
			bug.setDeveloper(tmpDeveloper);
			bug.setReviewer(tmpReviewer);
			bug.setProduct(tmpProduct);
			bug.setClassification(tmpClass);
			bug.setChange_ts(getCurrentTime());

			bug = bugRepo.saveAndFlush(bug);

			// add new message
			MessageModel msg = new MessageModel();
			if(oldstatus.equals(1) && bug.getStatus().getId().equals(2)){
				// new -> assign
				msg.setSender(bug.getTriager());
				msg.setReceiver(bug.getDeveloper());
				msg.setCreationts(getCurrentTime());
				msg.setTitle(bug.getTitle());
				msg.setContent("This is a new bug, please give out a solution. Bug ID: " + Integer.toString(bug.getId()) + " Priority: " + bug.getPriority().getDesc());
				logger.info("------------------ " + msg.toString());
				msgRepo.saveAndFlush(msg);
			}else if(oldstatus.equals(3) && bug.getStatus().getId().equals(2)){
				// verify -> assign
				msg.setSender(bug.getReviewer());
				msg.setReceiver(bug.getDeveloper());
				msg.setCreationts(getCurrentTime());
				msg.setTitle(bug.getTitle());
				msg.setContent("The solution didn't pass testing, please check again. Bug ID: " + Integer.toString(bug.getId()) + " Priority: " + bug.getPriority().getDesc());
				logger.info("------------------ " + msg.toString());
				msgRepo.saveAndFlush(msg);
			}else if(oldstatus.equals(2) && bug.getStatus().getId().equals(3)){
				// assign -> verify
				msg.setSender(bug.getDeveloper());
				msg.setReceiver(bug.getReviewer());
				msg.setCreationts(getCurrentTime());
				msg.setTitle(bug.getTitle());
				msg.setContent("This is an new solution, please test it. Bug ID: " + Integer.toString(bug.getId()) + " Priority: " + bug.getPriority().getDesc());
				logger.info("------------------ " + msg.toString());
				msgRepo.saveAndFlush(msg);
			}
			
			return ajaxReturn(true, id, "OK");
		} catch (Exception e) {
			return ajaxReturn(false, "", e.getMessage());
		}
	}

	@RequestMapping(value = "bugstatus", method = RequestMethod.GET)
	public BugStatusModel getStatusById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return bugstatusRepo.findOne(id);
	}

	@RequestMapping(value = "bugstatuslist", method = RequestMethod.GET)
	public List<BugStatusModel> getBugStatusList(HttpServletRequest request,
			@RequestParam(value = "typeid", required = false) String typeid,
			@RequestParam(value = "sm", required = false) String sm) {
		if (typeid != null && !typeid.trim().equals("")) {
			return bugRepo.findStatusList(typeid, sm);
		} else
			return bugstatusRepo.findAll();
	}

	@RequestMapping(value = "bugclasslist", method = RequestMethod.GET)
	public List<BugClassModel> getBugClassList(HttpServletRequest request) {
		return bugclassRepo.findAll();
	}

	@RequestMapping(value = "bugprioritylist", method = RequestMethod.GET)
	public List<BugPriorityModel> getBugPriorityList(HttpServletRequest request) {
		return bugpriorityRepo.findAll();
	}
}
