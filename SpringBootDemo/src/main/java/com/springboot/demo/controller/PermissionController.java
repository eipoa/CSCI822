/**
 * 
 */
package com.springboot.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class PermissionController {
	@RequestMapping(value = "permissions", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Auth/permissions");
		return mv;
	}
}
