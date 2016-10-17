/**
 * 
 */
package com.bugtrack.admin.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.*;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bugtrack.admin.dao.UserRepository;
import com.bugtrack.admin.model.RoleModel;
import com.bugtrack.admin.model.UserModel;

/**
 * @author Administrator
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	@PersistenceContext
//	private EntityManager em;
	@Autowired
	private UserRepository usersRepo;
	@Autowired
	HttpServletRequest request;

	// get user's detial info via username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			logger.info("------------------com.BugTracker "+username+" send login request");
			UserModel user = usersRepo.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("Cannot find the user!");
			}
			if(!user.getStatus().equals(1)){
				throw new DisabledException("The account is disabled");
			}
			logger.info("------------------com.BugTracker "+username+" is "+user.getRoleString());
			// get all authorities(role) related to this user
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			Collection<RoleModel> roles = user.getRoles();
			for (RoleModel role : roles) {
				SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getRolename().toUpperCase());
				auths.add(auth);
			}
			
//			SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_ADMIN");
//			SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_USER");
//			if (username.equals("123")) {
//				auths = new ArrayList<GrantedAuthority>();
//				auths.add(auth1);
//				logger.info(auths.toString());
//			}
//			if (username.equals("124")) {
//				auths = new ArrayList<GrantedAuthority>();
//				auths.add(auth2);
//				logger.info(auths.toString());
//			}
			
			// return the userdetial
			User userdetial = new User(user.getUsername(), user.getPassword(), true, true, true, true, auths);
			return userdetial;
		} catch (Exception e) {
			throw e;
		}
	}
}
