/**
 * 
 */
package com.bugtrack.admin.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class PublicFunction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	/**
	 * check ajax or normal
	 * 
	 * @param request
	 * @return
	 */
	public boolean isAjax() {
		Map<String, String> headermap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headermap.put(key, value);
		}
		logger.info(headermap.toString());

		if ((request.getParameter("isAjax") != null && request.getParameter("isAjax").equals("1"))
				|| (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").toLowerCase().equals("xmlhttprequest"))) {
		//if (request.getParameter("isAjax") != null && request.getParameter("isAjax").equals("1")){
			return true;
		} else {
			return false;
		}
	}

	public boolean isLogin() {
		if (session.getAttribute("USER_STATE") != null) {
			UserDetails userDetails = (UserDetails) session.getAttribute("USER_STATE");
			if (userDetails != null) {
				logger.info(userDetails.toString());
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}
}
