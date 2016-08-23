/**
 * 
 */
package com.springboot.demo.security;

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

import com.springboot.demo.dao.UserRepository;
import com.springboot.demo.model.UserModel;

/**
 * @author Administrator
 *
 */
public class CoustomUserDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository usersRepo;

	// get user's detial info via username
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		logger.info("++++++++++++++++++++++++++++++" + username + "+++++++++++++++++++++++++++++++");
		// keep the user's roles assigned by administrator
		// get all roles belong to the username, the following is just test code
		UserModel userObj = usersRepo.findByUsername(username);
		if (userObj == null) {
			throw new UsernameNotFoundException("Cannot find the user!");
		}
		
		// get all authorities related to this userObj
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_ADMIN");
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER");
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
		// org.springframework.security.core.userdetails.User.User
		// (String username, String password, boolean enabled, boolean
		// accountNonExpired,
		// boolean credentialsNonExpired, boolean accountNonLocked,
		// Collection<? extends GrantedAuthority> authorities)
		User user = new User(userObj.getUsername(), userObj.getPassword(), true, true, true, true, auths);
		logger.info("++++++++++++++++++++++++++++++ OK +++++++++++++++++++++++++++++++");

/*		if (user == null) {
			throw new UsernameNotFoundException("Username or password incorrect!");
		}*/

		return user;
	}
}
