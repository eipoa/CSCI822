/**
 * 
 */
package com.springboot.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.springboot.demo.ajax.AjaxAuthenticationFailureHandler;
import com.springboot.demo.ajax.AjaxAuthenticationSuccessHandler;

/**
 * @author Administrator
 *         http://satishab.blogspot.com.au/2012/10/part-4-securing-web-application-with.html
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AccessDecisionManager customAccessDecisionManager() {
		return new CustomAccessDecisionManager();
	}

	@Autowired
	private AjaxAuthenticationSuccessHandler authSuccessHandler;
	@Autowired
	private AjaxAuthenticationFailureHandler authFailureHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		web.ignoring().antMatchers("/css/**").antMatchers("/scripts/**").antMatchers("/images/**")
				.antMatchers("/data/**").antMatchers("/Public/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();

		// define accessDecisionManager, Specify that URLs are allowed by any
		// authenticated user
		http.authorizeRequests().anyRequest().authenticated().accessDecisionManager(customAccessDecisionManager());

		// the resources under the Public do not need permission
		http.authorizeRequests().antMatchers("/Public/**").permitAll();

		// define login pages
		http.csrf().disable();
		http.formLogin().loginPage("/Public/login").loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("username").passwordParameter("password").permitAll();
		http.formLogin().successHandler(authSuccessHandler);
		http.formLogin().failureHandler(authFailureHandler);

		// define logout pages
		http.logout().logoutUrl("/j_spring_security_logout").deleteCookies("JSESSIONID").logoutSuccessUrl("/login")
				.invalidateHttpSession(true);

		// session manage
		http.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
	}

	// register CoustomUserDetailService as a bean
	@Bean
	public UserDetailsService coustomUserDetailService() {
		return new CustomUserDetailService();
	}

	// user userDetailsService to Authenticate
	// but what is Authentication ? check password?? or some else
	// the next step is to check the role (accessDecisionManager)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(coustomUserDetailService());// .passwordEncoder(new
															// Md5PasswordEncoder());
	}
}
