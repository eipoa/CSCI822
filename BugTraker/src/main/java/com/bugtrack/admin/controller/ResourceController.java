/**
 * 
 */
package com.bugtrack.admin.controller;

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
public class ResourceController {
	@RequestMapping(value = "resources", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Auth/resources");
		return mv;
	}
}
