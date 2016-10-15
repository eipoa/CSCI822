/**
 * 
 */
package com.bugtrack.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
public class CustomErrorController implements ErrorController {
	@Value("${server.error.path:/Public/error}")
	private String PATH;

	@Autowired
	HttpServletRequest req;
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		System.out.println("-------------------------------XXXXXXXXXXXXXXXXXX "+req.getRequestURL());
		return this.getErrorPath();
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
}
