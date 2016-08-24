/**
 * 
 */
package com.springboot.demo.ajax;

import java.io.IOException;
import java.io.OutputStream;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@Component
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		/*Map<String, String> headermap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headermap.put(key, value);
		}
		logger.info(headermap.toString());*/
		
		if (request.getParameter("isAjax").equals("1")) {
			//logger.info("------------------com.BugTracker Login Failure!");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", false);
			map.put("info", exception.getMessage());
			map.put("data", "");
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------AjaxAuthenticationFailureHandler Login Failure!   " + jsonString);
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
		}else{
			logger.info("------------------AuthenticationFailureHandler Login Failure!");
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
