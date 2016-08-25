/**
 * 
 */
package com.springboot.demo.security;

import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.util.PublicFunction;

/**
 * @author Administrator
 *
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired  
	private PublicFunction pf;// = new PublicFunction();
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		/*Map<String, String> headermap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headermap.put(key, value);
		}
		logger.info(headermap.toString());*/

		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
		HttpSession session = request.getSession(true);  
		session.setAttribute("USER_STATE", userDetails);
		
		if (this.pf.isAjax()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", true);
			map.put("info", "login successfully.");
			map.put("data", getReturnUrl(request, response));
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------AjaxAuthenticationSuccessHandler Login Success!   " + jsonString);
			//OutputStream out = response.getOutputStream();
			//out.write(jsonString.getBytes());
			response.setContentType("application/json"); 
			response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonString);  
            response.getWriter().flush(); 
            super.clearAuthenticationAttributes(request);
		//} else {
			logger.info("------------------AuthenticationSuccessHandler Login Success!");
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			return request.getSession().getServletContext().getContextPath();
		}
		return savedRequest.getRedirectUrl();
	}

}
