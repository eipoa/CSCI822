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

import com.bugtrack.admin.dao.PermissionRepository;

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
	private PermissionRepository permissionRepo;

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		logger.info("------------------com.BugTracker AccessDecisionManager : check the permission");
		logger.info("------------------the user authentication : " + authentication.toString());
		logger.info("------------------the secured object : " + object.toString());
		logger.info("------------------the configuration attributes : " + configAttributes.toString());
		
		//?? for public?
		if (configAttributes.isEmpty()){
			return;
		}

		// get permission according to object, and then check authorization
		String url = ((FilterInvocation) object).getHttpRequest().getRequestURI();
		logger.info("------------------URI : "+ url);
		// String url1 = ((FilterInvocation) object).getFullRequestUrl();//
		// Url=/ fullUrl=http://localhost:8080/
		// find all permitted roles for this url
		//authentication
		//org.springframework.security.authentication.AnonymousAuthenticationToken@9055c2bc: Principal: anonymousUser; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@b364: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: null; Granted Authorities: ROLE_ANONYMOUS
		Collection<String> needRoles = permissionRepo.findAllRolename(url);
		for (GrantedAuthority gra : authentication.getAuthorities()) {
			// admin is always ok
			if(gra.getAuthority().trim().equals("ROLE_ADMIN")) return;
			for (String needRole : needRoles) {
				logger.info(needRole + "====" + gra.getAuthority());
				if (needRole.trim().equals(gra.getAuthority().trim())) {
					return;
				}
			}
		}
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
