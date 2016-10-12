/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.model.UserModel;
import com.bugtrack.admin.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Public")
public class PublicController extends CommonController {

	@Autowired
	private PublicFunction pf;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		if (!pf.isLogin()) {
			ModelAndView mv = new ModelAndView("Public/login");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("index");
			return mv;
		}
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "chpwd", method = RequestMethod.PUT)
	public String changePassword(
			@RequestParam(value = "oldpw", required = true) String oldpwd,
			@RequestParam(value = "newpw", required = true) String newpwd) throws Exception{
		try {
			UserModel user = userRepo.findById(this.getUserId());
			oldpwd = new Md5PasswordEncoder().encodePassword(oldpwd, null);
			if(!user.getPassword().equals(oldpwd)){
				return ajaxReturn(false, "1", "the old password is not correct");
			}
			newpwd = new Md5PasswordEncoder().encodePassword(newpwd, null);
			user.setPassword(newpwd);
			userRepo.saveAndFlush(user);
			return ajaxReturn(true, "1", "OK");
		}catch (Exception e){
			return ajaxReturn(false, "1", "//////////////");//e.getMessage());
		}
	}

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ModelAndView error() {
		ModelAndView mv = new ModelAndView("Public/error");
		return mv;
	}

	@RequestMapping(value = "403", method = RequestMethod.GET)
	public ModelAndView error403() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", "Access Denied/Forbidden");
		mv.addObject("path", request.getRequestURI());
		mv.addObject("timestamp", new Date().toString());
		mv.addObject("status", response.getStatus());
		Integer num = this.getCountTask();
		if (num.intValue() > 0)
			mv.addObject("tasks", num);
		String fn = this.getFullname();
		if (fn != null)
			mv.addObject("fullname", fn);
		mv.setViewName("Public/403");
		return mv;
	}
}
