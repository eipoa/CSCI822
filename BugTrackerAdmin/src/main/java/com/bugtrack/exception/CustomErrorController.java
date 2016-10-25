/**
 * 
 */
package com.bugtrack.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@RestController
public class CustomErrorController implements ErrorController {
	@Value("${server.error.path:/Public/error}")
	private String PATH;

	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest req, HttpServletResponse rep) {
		ModelAndView mv = new ModelAndView("Public/error");
		mv.addObject("path", req.getRequestURI());
		mv.addObject("timestamp", new Date().toString());
		mv.addObject("status", rep.getStatus());
		return mv;
	}
	
	@RequestMapping(value = "/Public/error", method = RequestMethod.GET)
	public ModelAndView publicError(HttpServletRequest req, HttpServletResponse rep) {
		ModelAndView mv = new ModelAndView("Public/error");
		mv.addObject("path", req.getRequestURI());
		mv.addObject("timestamp", new Date().toString());
		mv.addObject("status", "404");
		return mv;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
