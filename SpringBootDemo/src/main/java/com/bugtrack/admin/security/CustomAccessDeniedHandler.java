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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@Component
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired  
	private PublicFunction pf;// = new PublicFunction();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (this.pf.isAjax()) {
			// logger.info("------------------com.BugTracker Login Failure!");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", false);
			map.put("info", accessDeniedException.getMessage());
			map.put("data", "");
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------Ajax CustomAccessDeniedHandler!   " + jsonString);
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
		} else {
			//????? 为什么走不到这里
			logger.info("------------------AccessDeniedHandler   ");
			super.handle(request, response, accessDeniedException);
		}
	}
}
