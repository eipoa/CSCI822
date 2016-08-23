/**
 * 
 */
package com.springboot.demo.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
	/*private AuthenticationFailureHandler defaultHandler;

	public AjaxAuthenticationFailureHandler() {

	}

	public AjaxAuthenticationFailureHandler(AuthenticationFailureHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
	}*/
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("------------------com.BugTracker Login Failure!");
		PrintWriter writer = response.getWriter();
        writer.write(exception.getMessage());
        writer.flush();
		/*if ("true".equals(request.getHeader("X-Ajax-call"))) {
			response.getWriter().print("{success:false, targetUrl : \'\\\'}");
			response.getWriter().flush();
		} else {
			defaultHandler.onAuthenticationFailure(request, response, exception);
		}*/
	}

}
