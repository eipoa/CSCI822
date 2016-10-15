/**
 * 
 */
package com.bugtrack.admin.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.bugtrack.admin.dao.ResourceRepository;
import com.bugtrack.admin.model.ResourceModel;
import com.bugtrack.admin.model.RoleModel;

//import org.springframework.security.access.ConfigAttribute; 
import org.springframework.security.web.FilterInvocation;

/**
 * @author Administrator
 *
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ResourceRepository resRepo;

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		logger.info("------------------com.BugTracker AccessDecisionManager : check the permission");
		logger.info("------------------the user authentication : " + authentication.toString());
		logger.info("------------------the secured object : " + object.toString());
		logger.info("------------------the configuration attributes : " + configAttributes.toString());

		// ?? for public?
		if (configAttributes.isEmpty()) {
			return;
		}

		// get permission according to object, and then check authorization
		String url = ((FilterInvocation) object).getHttpRequest().getRequestURI();
		logger.info("------------------URI : " + url);
		// String url1 = ((FilterInvocation) object).getFullRequestUrl();//
		// Url=/ fullUrl=http://localhost:8080/
		// find all permitted roles for this url
		// authentication
		// org.springframework.security.authentication.AnonymousAuthenticationToken@9055c2bc:
		// Principal: anonymousUser; Credentials: [PROTECTED]; Authenticated:
		// true; Details:
		// org.springframework.security.web.authentication.WebAuthenticationDetails@b364:
		// RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: null; Granted
		// Authorities: ROLE_ANONYMOUS
		// try{
		ResourceModel resources = resRepo.findByResourceAndStatus(url, 1);
		if (resources != null) {
			Collection<RoleModel> needRoles = resources.getRoles();
			for (GrantedAuthority gra : authentication.getAuthorities()) {
				// admin is always ok
				logger.info("------------------My Role : " + gra.getAuthority().trim());
				if (gra.getAuthority().trim().equals("ROLE_ADMIN"))
					return;
				for (RoleModel needRole : needRoles) {
					logger.info(needRole.getRolename() + "====" + gra.getAuthority());
					if (needRole.getRolename().trim().toUpperCase().equals(gra.getAuthority())) {
						return;
					}
				}
			}
		}
		// }catch(Exception e){
		//
		// }
		logger.info("------------------permission denied, throw AccessDeniedException");
		throw new AccessDeniedException("Access Denied");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
}
