/**
 * 
 */
package com.bugtrack.admin.security;

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

import com.bugtrack.admin.util.PublicFunction;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 * only logined user can go here
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", false);
			map.put("info", accessDeniedException.getMessage());
			map.put("data", "403");
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------Ajax CustomAccessDeniedHandler!   " + jsonString);
			response.setStatus(403);
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
		} else {
			logger.info("------------------AccessDeniedHandler   ");
			logger.info("------------------URI   " + request.getRequestURI());
			
			this.setErrorPage("/Admin/Public/403");//test parameter
			request.setAttribute("oriurl", request.getRequestURI());
			super.handle(request, response, accessDeniedException);
		}
	}
	// ExceptionTranslationFilter(import org.springframework.security.web.access.ExceptionTranslationFilter;)
	//        1. Access is denied (user is anonymous); redirecting to authentication entry point
	//		  2. Access is denied (user is not anonymous); delegating to AccessDeniedHandler
	
}
