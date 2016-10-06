/**
 * 
 */
package com.bugtrack.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Public")
public class PublicController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired  
	private PublicFunction pf;// = new PublicFunction();
	
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

	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ModelAndView error() {
		ModelAndView mv = new ModelAndView("Public/error");
		return mv;
	}
}
