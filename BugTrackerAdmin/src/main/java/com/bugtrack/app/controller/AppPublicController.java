/**
 * 
 */
package com.bugtrack.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugCommentModel;
import com.bugtrack.model.BugsModel;
import com.bugtrack.model.SysNoteModel;
import com.bugtrack.model.UserModel;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/App/Public")
public class AppPublicController extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		if (!isLogin()) {
			ModelAndView mv = new ModelAndView("App/Public/login");
			return mv;
		} else {
			rep.sendRedirect("/");
			return null;
		}
	}

	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public ModelAndView signup(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		if (!isLogin()) {
			ModelAndView mv = new ModelAndView("App/Public/signup");
			return mv;
		} else {
			rep.sendRedirect("/");
			return null;
		}
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "chpwd", method = RequestMethod.PUT)
	public String changePassword(@RequestParam(value = "oldpw", required = true) String oldpwd,
			@RequestParam(value = "newpw", required = true) String newpwd) throws Exception {
		try {
			UserModel user = userRepo.findById(this.getUserId());
			oldpwd = new Md5PasswordEncoder().encodePassword(oldpwd, null);
			if (!user.getPassword().equals(oldpwd)) {
				return ajaxReturn(false, "1", "the old password is not correct");
			}
			newpwd = new Md5PasswordEncoder().encodePassword(newpwd, null);
			user.setPassword(newpwd);
			userRepo.saveAndFlush(user);
			return ajaxReturn(true, "1", "OK");
		} catch (Exception e) {
			return ajaxReturn(false, "1", "//////////////");// e.getMessage());
		}
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

	@RequestMapping(value = "error", method = { RequestMethod.POST, RequestMethod.GET }) // ,
																							// method
																							// =
																							// RequestMethod.GET
	public ModelAndView error(HttpServletRequest req, HttpServletResponse rep) {
		String path = "App/Public/error";
		if (this.isLogin())
			path = "App/Public/erroronline";
		ModelAndView mv = new ModelAndView(path);
		mv.addObject("path", req.getRequestURI());
		mv.addObject("timestamp", new Date().toString());
		mv.addObject("status", this.getStatus(req));
		return mv;
	}

	@RequestMapping(value = "403", method = RequestMethod.GET)
	public ModelAndView error403(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "Access Denied/Forbidden");
		logger.info("------------------------------ error error error error" + req.toString());
		// req.getPathTranslated()
		// String remoteAddress=request.getHeader("referer");
		// String servletPath=request.getServletPath();
		// String remoteUser=request.getRemoteUser();
		// String requestURI=request.getRequestURI();
		// Enumeration<String> aaa = request.getHeaderNames();
		String forbiddenURI = request.getAttribute("oriurl").toString();
		mv.addObject("path", req.getRequestURI());
		mv.addObject("timestamp", new Date().toString());
		mv.addObject("status", response.getStatus());
		mv.addObject("path", forbiddenURI);

		if (this.isAjax()) {
			throw new Exception("403 Access Denied/Forbidden");
		}
		Integer num = this.getCountTask();
		if (num.intValue() > 0)
			mv.addObject("tasks", num);
		String fn = this.getFullname();
		if (fn != null)
			mv.addObject("fullname", fn);
		mv.setViewName("App/Public/403");
		return mv;
	}

	@RequestMapping(value = "regist", method = { RequestMethod.POST })
	public String regist(HttpServletRequest req, HttpServletResponse rep, UserModel user) throws Exception {
		user.setLast_name("");
		user.setRoles(this.roleRepo.findAllById(6));
		user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), null));
		user.setUsername(user.getEmail());
		user.setCreate_ts(this.getCurrentTime());
		user = userRepo.saveAndFlush(user);
		return ajaxReturn(true, user.getId().toString(), "Sign up successfully");
	}

	@RequestMapping(value = "comments", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView commentList(HttpServletRequest req, HttpServletResponse rep,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "rows", required = true, defaultValue = "20") Integer rows) {
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.ASC, "creationts"));
		Sort sort = new Sort(orders);
		Pageable pageable = new PageRequest(page, rows, sort);
		Page<BugCommentModel> commentList = bugcommentRepo.findAllByBug(bugRepo.findOne(id), pageable);
		ModelAndView mv = new ModelAndView("App/Public/comments");
		mv.addObject("pages", commentList);
		List<UserModel> topuse = userRepo.findTop10ByOrderByReputationDesc();
		mv.addObject("topusers", topuse);
		List<SysNoteModel> note = sysnRepo.findTop3ByExpireGreaterThan(getCurrentTime());
		if (note != null)
			mv.addObject("bid", id);
		mv.addObject("news", note);
		if (isLogin()) {
			mv.addObject("fullname", this.getFullname());
		}
		BugsModel bug = bugRepo.findById(id);
		mv.addObject("bug", bug);
		return mv;
	}
	

}
