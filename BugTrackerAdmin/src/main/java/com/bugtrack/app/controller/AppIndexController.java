package com.bugtrack.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugCommentModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugSeverityModel;
import com.bugtrack.model.BugStatusModel;
import com.bugtrack.model.BugsModel;
import com.bugtrack.model.MessageModel;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;
import com.bugtrack.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/App")
public class AppIndexController extends CommonController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		if (isLogin()) {
			/*
			 * Integer num = this.getCountTask(); if(num.intValue()>0)
			 * mv.addObject("tasks", this.getCountTask());
			 */
			mv.addObject("fullname", this.getFullname());
		}
		mv.addObject("productList", this.productNameRepo.findAll());
		return mv;
	}

	@RequestMapping(value = "Bug/addcomment", method = RequestMethod.POST)
	public String addcomment(HttpServletRequest req, HttpServletResponse rep,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "descp", required = true) String thetext) throws Exception {
		BugCommentModel comment = new BugCommentModel();
		comment.setBug(bugRepo.findOne(id));
		comment.setThetext(thetext);
		comment.setCreationts(this.getCurrentTime());
		comment.setSubmitter(this.getUser());
		comment = this.bugcommentRepo.saveAndFlush(comment);
		return ajaxReturn(true, comment.getId().toString(), "OK");
	}

	@RequestMapping(value = "Bug/addbug", method = RequestMethod.GET)
	public ModelAndView addbug() {
		ModelAndView mv = new ModelAndView("App/Bug/addbug");
		if (isLogin()) {
			mv.addObject("fullname", this.getFullname());
		}
		// mv.addObject("productList", this.productNameRepo.findAll());
		return mv;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "Bug/submit", method = RequestMethod.POST)
	public String submitBug(HttpServletRequest req, HttpServletResponse rep,
			@RequestParam(value = "product_name_id", required = true) Integer product_name_id,
			@RequestParam(value = "product_version", required = true) String product_version,
			@RequestParam(value = "product_os_id", required = false, defaultValue = "1") Integer product_os_id,
			@RequestParam(value = "product_class_id", required = false, defaultValue = "6") Integer product_class_id,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "short_desc", required = false, defaultValue = "") String short_desc)
			throws Exception {
		// status
		BugStatusModel tmpStatus = bugstatusRepo.findOne(1);
		// classification
		BugClassModel tmpClass = bugclassRepo.findOne(product_class_id);
		// priority
		BugPriorityModel tmpPriority = bugpriorityRepo.findOne(1);
		// product
		ProductNameModel pname = productNameRepo.findOne(product_name_id);
		System.out.println(pname.getName());
		ProductOsModel pos = productOsRepo.findOne(product_os_id);
		System.out.println(pos.getOsname());
		ProductModel tmpProduct = productRepo.findByNameAndVersionAndOs(pname, product_version, pos);
		if(tmpProduct==null)
			tmpProduct = productRepo.findTop1ByNameAndVersion(pname, product_version);
		BugSeverityModel tmpSeverity = sevRepo.findOne(2);
		// developer
		UserModel reporter = this.getUser();

		BugsModel bug = new BugsModel();
		// modifiy bugs
		bug.setRank(0);
		bug.setTitle(title);
		bug.setShortdesc(short_desc);
		bug.setPriority(tmpPriority);
		bug.setSeverity(tmpSeverity);
		bug.setStatus(tmpStatus);
		bug.setProduct(tmpProduct);
		bug.setClassification(tmpClass);
		bug.setReporter(reporter);
		bug.setCreationts(getCurrentTime());
		bug.setChange_ts(getCurrentTime());
		bug.setTriager(userRepo.findByUsername("123"));

		bug = bugRepo.saveAndFlush(bug);
		
		// command
		this.addcomment(req, rep, bug.getId(), short_desc);
		return ajaxReturn(true, bug.getId().toString(), "OK");
		// } catch (Exception e) {
		// return ajaxReturn(false, "", e.getMessage());
		// }
	}
	
	@RequestMapping(value = "Bug/vote", method = { RequestMethod.POST })
	public String vote(HttpServletRequest req, HttpServletResponse rep, @RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "v", required = true) Integer v) throws Exception {
		BugsModel bug = bugRepo.findById(id);
		Integer vote = bug.getVote();
		vote += v;
		vote = vote<0?0:vote;
		bug.setVote(vote);
		bug = bugRepo.saveAndFlush(bug);
		return ajaxReturn(true, bug.getVote().toString(), "OK");
	}
}
