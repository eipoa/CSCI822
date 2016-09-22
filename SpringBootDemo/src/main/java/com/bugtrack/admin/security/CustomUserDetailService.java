/**
 * 
 */
package com.bugtrack.admin.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bugtrack.admin.dao.UserRepository;
import com.bugtrack.admin.model.UserModel;

/**
 * @author Administrator
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository usersRepo;

	// get user's detial info via username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			logger.info("------------------com.BugTracker "+username+" send login request");
			UserModel userObj = usersRepo.findByUsername(username);
			if (userObj == null) {
				throw new UsernameNotFoundException("Cannot find the user!");
			}
			logger.info("------------------com.BugTracker "+username+" is "+userObj.getRole().getRoleName());
			// get all authorities related to this userObj
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_ADMIN");
			SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_USER");
			if (username.equals("123")) {
				auths = new ArrayList<GrantedAuthority>();
				auths.add(auth1);
				logger.info(auths.toString());
			}
			if (username.equals("124")) {
				auths = new ArrayList<GrantedAuthority>();
				auths.add(auth2);
				logger.info(auths.toString());
			}
			
			// return the userdetial
			User user = new User(userObj.getUsername(), userObj.getPassword(), true, true, true, true, auths);
			/*if (user == null) {
				throw new UsernameNotFoundException("Username or password incorrect!");
			}*/
			return user;
		} catch (Exception e) {
			throw e;
		}
	}
}
