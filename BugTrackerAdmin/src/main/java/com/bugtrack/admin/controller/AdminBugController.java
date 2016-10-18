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

import com.bugtrack.exception.CustomJsonException;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugPatchModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugStatusModel;
import com.bugtrack.model.BugsModel;
import com.bugtrack.model.MessageModel;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.UserModel;
import com.bugtrack.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Admin/Bug")
public class AdminBugController extends AdminCommonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * the main view of assign bugs
	 * 
	 * @return template of /Bug
	 * @throws CustomJsonException
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Map<String, Object> bugList(HttpServletRequest request, PageContent page,
			@RequestParam(value = "pname", required = false) String pname,
			@RequestParam(value = "pver", required = false) String pver,
			@RequestParam(value = "pos", required = false) String pos,
			@RequestParam(value = "bstatus", required = false) String bstatus,
			@RequestParam(value = "btitle", required = false) String btitle,
			@RequestParam(value = "bpriority", required = false) String bpriority,
			@RequestParam(value = "bcategory", required = false) String bcategory,
			@RequestParam(value = "bid", required = false) String bid,
			@RequestParam(value = "typeid", required = false) String typeid,
			@RequestParam(value = "bstart", required = false) String cts1,
			@RequestParam(value = "bend", required = false) String cts2) throws Exception {
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
		if (bpriority != null && !bpriority.trim().equals(""))
			keywords.put("bpriority", bpriority);
		if (bcategory != null && !bcategory.trim().equals(""))
			keywords.put("bcategory", bcategory);
		if (bid != null && !bid.trim().equals(""))
			keywords.put("bid", bid);
		if (cts1 != null && !cts1.trim().equals("")) {
			keywords.put("cts1", cts1);
		}
		if (cts2 != null && !cts2.trim().equals("")) {
			keywords.put("cts2", cts2);
		}
		if (typeid != null && !typeid.trim().equals("")) {
			if (typeid.trim().equals("1")) {
				keywords.put("triager", Integer.toString(getUserId()));
				// triager can retrive all status
				// if (bstatus != null && !bstatus.trim().equals(""))
				// keywords.put("bstatus", bstatus);
			} else if (typeid.trim().equals("2")) {
				keywords.put("developer", Integer.toString(getUserId()));
				// developer can only retrive assigned status
				// keywords.put("bstatus", "2");
			} else if (typeid.trim().equals("3")) {
				keywords.put("reviewer", Integer.toString(getUserId()));
				// reviewer can only retrive verify status
				// keywords.put("bstatus", "3");
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
			@RequestParam(value = "rank", required = false) String rank,
			@RequestParam(value = "patch_desc", required = false) String patch_desc,
			@RequestParam(value = "patch_url", required = false) String patch_url,
			@RequestParam(value = "patch_status", required = false) Integer patch_status) throws Exception {
		// try {
		BugsModel bug = bugRepo.findById(new Integer(id));
		if (bug == null) {
			throw new Exception("cannot find the bug");
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
		boolean mustHaveSolution = false;
		if (bug.getStatus().getId().equals(2) && tmpStatus.getId().equals(3)) {
			mustHaveSolution = true;
		}
		bug.setStatus(tmpStatus);
		bug.setDeveloper(tmpDeveloper);
		bug.setReviewer(tmpReviewer);
		bug.setProduct(tmpProduct);
		bug.setClassification(tmpClass);
		bug.setChange_ts(getCurrentTime());

		// bug = bugRepo.saveAndFlush(bug);
		// save patch
		if (patch_desc != null && patch_desc.trim().length() > 0) {
			patch_desc = patch_desc.replace("<p>", "");
			patch_desc = patch_desc.replace("</p>", "");
			patch_desc = patch_desc.replace("<br/>", "");

			// one message
			patch_desc = "<div class=\"bs-callout bs-callout-patch\">" + patch_desc;
			patch_desc = patch_desc + "<p style=\"text-align: right;\">" + bug.getChange_ts() + "</p>";
			patch_desc = patch_desc + "<p style=\"text-align: right;\">" + this.getFullname() + "</p>";
			patch_desc = patch_desc + "</div>";
		}
		BugPatchModel patch = new BugPatchModel();
		if (patch_url != null)
			patch.setUrl(patch_url);
		if (patch_desc != null)
			patch.setDescription(patch_desc);
		patch.setCreation_ts(bug.getChange_ts());
		if (bug.getStatus().getId() == 4 || bug.getStatus().getId() == 5)
			// bug is over
			patch.setStatus(1);
		else
			patch.setStatus(0);
		// if there is a patch
		if ((patch.getDescription() != null && !patch.getDescription().trim().equals(""))
				|| (patch.getUrl() != null && !patch.getUrl().trim().equals("")))
			bug.addSolution(patch);
		else if (mustHaveSolution) {
			// mustHaveSolution, but no solution
			throw new Exception("Please submmit solutions");
		}
		bug = bugRepo.saveAndFlush(bug);
		// add new message
		MessageModel msg = new MessageModel();
		msg.setSender(bug.getDeveloper());
		msg.setReceiver(bug.getReviewer());
		msg.setCreationts(bug.getChange_ts());
		msg.setTitle(bug.getTitle());
		if (oldstatus.equals(1) && bug.getStatus().getId().equals(2)) {
			// new -> assign
			msg.setContent(bug.getMsg(1, 2));
			msgRepo.saveAndFlush(msg);
		} else if (oldstatus.equals(3) && bug.getStatus().getId().equals(2)) {
			// verify -> assign
			msg.setContent(bug.getMsg(3, 2));
			msgRepo.saveAndFlush(msg);
		} else if (oldstatus.equals(2) && bug.getStatus().getId().equals(3)) {
			// assign -> verify
			msg.setContent(bug.getMsg(2, 3));
			msgRepo.saveAndFlush(msg);
		}

		return ajaxReturn(true, id, "OK");
		// } catch (Exception e) {
		// return ajaxReturn(false, "", e.getMessage());
		// }
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
		return bugclassRepo.findAllByOrderByIdAsc();
	}

	@RequestMapping(value = "bugprioritylist", method = RequestMethod.GET)
	public List<BugPriorityModel> getBugPriorityList(HttpServletRequest request) {
		return bugpriorityRepo.findAllByOrderByIdAsc();
	}

	@RequestMapping(value = "solution", method = RequestMethod.GET)
	public List<BugPatchModel> getCurSolution(@RequestParam(value = "id", required = true) Integer id) {
		return patchRepo.findAllByBug_id(id);
	}

	@RequestMapping(value = "solutions", method = RequestMethod.GET)
	public String getCurSolution01(@RequestParam(value = "id", required = true) Integer id) {
		String ret = "";
		List<BugPatchModel> patchs = patchRepo.findAllByBug_id(id);
		for (BugPatchModel p : patchs) {
			if (p.getDescription() != null)
				ret += p.getDescription();
			if (p.getReply() != null)
				ret += p.getReply();
			ret += "<input id='cursol_id' class='easyui-textbox' type='hidden' value='" + p.getId() + "'>";
			// ret += "<input id='cursol_reply_url' class='easyui-textbox'
			// type='hidden'>";
			break;
		}
		return ret;
	}

	// @RequestMapping(value = "getreply", method = RequestMethod.GET)
	// public String getReply01(@RequestParam(value = "id", required = true)
	// Integer id) {
	// String ret = "";
	// List<BugPatchModel> patchs = patchRepo.findAllByBug_id(id);
	// for (BugPatchModel p : patchs) {
	// ret += p.getDescription();
	// ret += p.getReply();
	// ret += "<input id='cursol_id' class='easyui-textbox' type='hidden'
	// value='"+p.getId()+"'>";
	// break;
	// }
	// return ret;
	// }

	@RequestMapping(value = "reply", method = RequestMethod.POST)
	public String savePatchReply(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "reply", required = false) String reply) throws Exception {
		BugPatchModel patch = patchRepo.findOne(id);
		if (patch == null)
			throw new Exception("cannot find the patch");

		if (reply != null && reply.trim().length() > 0) {
			reply = reply.replace("<p>", "");
			reply = reply.replace("</p>", "");
			reply = reply.replace("<br/>", "");

			// one message
			reply = "<div class=\"bs-callout bs-callout-reply\">" + reply;
			reply = reply + "<p style=\"text-align: right;\">" + this.getCurrentTime() + "</p>";
			reply = reply + "<p style=\"text-align: right;\">" + this.getFullname() + "</p>";
			reply = reply + "</div>";
			patch.setReply(reply);
			patch = patchRepo.saveAndFlush(patch);
		}
		return ajaxReturn(true, id.toString(), "OK");
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
