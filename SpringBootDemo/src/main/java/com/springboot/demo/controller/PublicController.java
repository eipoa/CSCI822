/**
 * 
 */
package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Public")
public class PublicController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired  
	private PublicFunction pf;// = new PublicFunction();
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		if (!pf.isLogin()) {
			logger.info("--------------- null null null ------------");
			ModelAndView mv = new ModelAndView("Public/login");
			return mv;
		} else {
			logger.info("--------------- ok ok ok ------------");
			ModelAndView mv = new ModelAndView("index");
			return mv;
		}
	}

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ModelAndView error() {
		ModelAndView mv = new ModelAndView("Public/error");
		return mv;
	}
}
