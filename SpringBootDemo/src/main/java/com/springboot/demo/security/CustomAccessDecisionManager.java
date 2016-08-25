/**
 * 
 */
package com.springboot.demo.security;

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

import com.springboot.demo.dao.PermissionRepository;
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
		logger.info("------------------com.BugTracker AccessDecisionManager");
		/*if (null == configAttributes) {
			return;
		}*/

		// 根据object取得资源所需的角色，进行判断
		logger.info("------------------"+object.toString());
		String url = ((FilterInvocation) object).getHttpRequest().getRequestURI();
		logger.info("------------------"+((FilterInvocation) object).getHttpRequest().getRequestURI());
		// String url1 = ((FilterInvocation) object).getFullRequestUrl();//
		// Url=/ fullUrl=http://localhost:8080/
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
