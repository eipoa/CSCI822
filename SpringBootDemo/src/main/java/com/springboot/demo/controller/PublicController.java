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

import com.springboot.demo.dao.UserRepository;
import com.springboot.demo.model.UserModel;


/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Public")
public class PublicController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public ModelAndView login(){	
		ModelAndView mv = new ModelAndView("Public/login");    
		return mv;
	}

}
