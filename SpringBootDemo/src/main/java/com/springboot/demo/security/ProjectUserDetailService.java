/**
 * 
 */
package com.springboot.demo.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Administrator
 *
 */
public class ProjectUserDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	// get user's detial info via username
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		logger.info("++++++++++++++++++++++++++++++" + username + "+++++++++++++++++++++++++++++++");
		// keep the user's roles assigned by administrator
		// get all roles belong to the username, the following is just test code
		// it should be replaced by reading from Mysql
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_ADMIN");
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER");
		if (username.equals("123")) {
			auths = new ArrayList<GrantedAuthority>();
			auths.add(auth1);
			auths.add(auth2);
			logger.info(auths.toString());
		}

		// return the userdetial
		// org.springframework.security.core.userdetails.User.User
		// (String username, String password, boolean enabled, boolean
		// accountNonExpired,
		// boolean credentialsNonExpired, boolean accountNonLocked,
		// Collection<? extends GrantedAuthority> authorities)
		// someone implement the UserDitials interface, but the User class has been implemented.
		User currentuser = new User(username, "123", true, true, true, true, auths);
		logger.info("++++++++++++++++++++++++++++++ OK +++++++++++++++++++++++++++++++");
		/*if (currentuser == null) {
            throw new UsernameNotFoundException("Username or password incorrect!");
        }*/
		return currentuser;
	}
}
