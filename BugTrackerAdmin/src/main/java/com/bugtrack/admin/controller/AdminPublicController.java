/**
 * 
 */
package com.bugtrack.admin.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.exception.CustomJsonException;
import com.bugtrack.model.UserModel;
import com.bugtrack.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Admin/Public")
public class AdminPublicController extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PublicFunction pf;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!isLogin()) {
			ModelAndView mv = new ModelAndView("Admin/Public/login");
			return mv;
		} else {
			response.sendRedirect("Admin/");
			return null;
		}
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "chpwd", method = RequestMethod.PUT)
	public String changePassword(@RequestParam(value = "oldpw", required = true) String oldpwd,
			@RequestParam(value = "newpw", required = true) String newpwd) throws Exception {
		UserModel user = userRepo.findById(this.getUserId());
		oldpwd = new Md5PasswordEncoder().encodePassword(oldpwd, null);
		if (!user.getPassword().equals(oldpwd)) {
			return ajaxReturn(false, "1", "the old password is not correct");
		}
		newpwd = new Md5PasswordEncoder().encodePassword(newpwd, null);
		user.setPassword(newpwd);
		userRepo.saveAndFlush(user);
		return ajaxReturn(true, "1", "OK");
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

	@RequestMapping(value = "error", method = { RequestMethod.POST, RequestMethod.GET }) 
	public ModelAndView error(HttpServletRequest req, HttpServletResponse rep) {
		String path = "Admin/Public/error";
		if (this.isLogin())
			path = "Admin/Public/erroronline";
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
			throw new CustomJsonException("403 Access Denied/Forbidden");
		}
		Integer num = this.getCountTask();
		if (num.intValue() > 0)
			mv.addObject("tasks", num);
		String fn = this.getFullname();
		if (fn != null)
			mv.addObject("fullname", fn);
		mv.setViewName("Admin/Public/403");
		return mv;
	}
}
