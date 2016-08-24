/**
 * 
 */
package com.springboot.demo.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		if (request.getParameter("isAjax").equals("1")) {
			//logger.info("------------------com.BugTracker Login Failure!");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", false);
			map.put("info", accessDeniedException.getMessage());
			map.put("data", "");
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------com.BugTracker 403!   " + jsonString);
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
		}else{
			super.handle(request, response, accessDeniedException);
		}
	}
}
