/**
 * 
 */
package com.bugtrack.admin.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.bugtrack.admin.util.PublicFunction;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PublicFunction pf = new PublicFunction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.logout.
	 * LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		// TODO Auto-generated method stub
		if (this.pf.isAjax()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", true);
			map.put("info", "login successfully.");
			map.put("data", getReturnUrl(request, response));
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(map);
			logger.info("------------------AjaxAuthenticationSuccessHandler Login Success!   " + jsonString);
			// OutputStream out = response.getOutputStream();
			// out.write(jsonString.getBytes());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonString);
			response.getWriter().flush();
		} else {
			logger.info("------------------AuthenticationSuccessHandler Login Success!");
			//super.onLogoutSuccess(request, response, authentication);
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
