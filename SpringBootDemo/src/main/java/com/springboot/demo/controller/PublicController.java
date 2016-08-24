/**
 * 
 */
package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Public")
public class PublicController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getCurrentUsername() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "";
		} else {
			logger.info("---------------------- " + SecurityContextHolder.getContext().getAuthentication().getName());
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		//this.getCurrentUsername();
		ModelAndView mv = new ModelAndView("Public/login");
		return mv;
	}

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ModelAndView error() {
		//this.getCurrentUsername();
		ModelAndView mv = new ModelAndView("Public/error");
		return mv;
	}
}
