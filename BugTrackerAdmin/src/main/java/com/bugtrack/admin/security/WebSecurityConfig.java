/**
 * 
 */
package com.bugtrack.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Administrator
 *         http://satishab.blogspot.com.au/2012/10/part-4-securing-web-application-with.html
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{// extends WebSecurityConfigurerAdapter 

//	@Bean
//	public static AccessDecisionManager customAccessDecisionManager() {
//		return new CustomAccessDecisionManager();
//	}
//
//	@Autowired
//	private static CustomAuthenticationSuccessHandler authSuccessHandler;
//	@Autowired
//	private static CustomAuthenticationFailureHandler authFailureHandler;
//	@Autowired
//	private static CustomAccessDeniedHandler accessDeniedHandler;

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
		auth.userDetailsService(coustomUserDetailService()).passwordEncoder(new Md5PasswordEncoder());
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		// set permission rules
//		web.ignoring().antMatchers("/css/**").antMatchers("/scripts/**").antMatchers("/images/**")
//				.antMatchers("/fonts/**").antMatchers("/Admin/Public/**").antMatchers("/Upload/**").antMatchers("/")
//				.antMatchers("/App/Public/**").antMatchers("/Public/**");
//	}
	
	
	@Configuration
    @Order(1)                                                        
    public static class AppWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Bean
		public AccessDecisionManager customAccessDecisionManager() {
			return new CustomAccessDecisionManager();
		}

		@Autowired
		private CustomAuthenticationSuccessHandler authSuccessHandler;
		@Autowired
		private CustomAuthenticationFailureHandler authFailureHandler;
		@Autowired
		private CustomAccessDeniedHandler accessDeniedHandler;
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			// set permission rules
			web.ignoring().antMatchers("/css/**").antMatchers("/scripts/**").antMatchers("/images/**")
					.antMatchers("/fonts/**").antMatchers("/Upload/**").antMatchers("/")
					.antMatchers("/App/Public/**").antMatchers("/Public/**");
		}
		
        protected void configure(HttpSecurity http) throws Exception {
        	http.headers().frameOptions().sameOrigin();
        	// URL pattern without needs of permission
        	http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    		http.authorizeRequests().antMatchers("/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("/App/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("/css/**").permitAll();
    		http.authorizeRequests().antMatchers("/scripts/**").permitAll();
    		http.authorizeRequests().antMatchers("/images/**").permitAll();
    		http.authorizeRequests().antMatchers("/fonts/**").permitAll();
    		http.authorizeRequests().antMatchers("/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("Upload/**").permitAll();
    		http.authorizeRequests().antMatchers("/").permitAll();
    		// URL pattern with needs of permission
        	http.antMatcher("/App/**").authorizeRequests().anyRequest().authenticated().accessDecisionManager(customAccessDecisionManager());
    		// define login pages
    		http.csrf().disable();
    		http.formLogin().loginPage("/App/Public/login").loginProcessingUrl("/j_spring_security_check")
    				.usernameParameter("username").passwordParameter("password").permitAll();
    		http.formLogin().successHandler(authSuccessHandler);
    		http.formLogin().failureHandler(authFailureHandler);
    
    		// define logout pages***
    		http.logout().logoutUrl("/j_spring_security_logout")
    				.deleteCookies("JSESSIONID").logoutSuccessUrl("/").invalidateHttpSession(true);
    
    		// session manage***
    		http.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
        }
    }

	@Configuration
    @Order(2)                                                        
    public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Bean
		public AccessDecisionManager customAccessDecisionManager() {
			return new CustomAccessDecisionManager();
		}

		@Autowired
		private CustomAuthenticationSuccessHandler authSuccessHandler;
		@Autowired
		private CustomAuthenticationFailureHandler authFailureHandler;
		@Autowired
		private CustomAccessDeniedHandler accessDeniedHandler;
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			// set permission rules
			web.ignoring().antMatchers("/css/**").antMatchers("/scripts/**").antMatchers("/images/**")
					.antMatchers("/fonts/**").antMatchers("/Upload/**").antMatchers("/")
					.antMatchers("/Admin/Public/**").antMatchers("/Public/**");
		}
		
        protected void configure(HttpSecurity http) throws Exception {
        	http.headers().frameOptions().sameOrigin();
        	// URL pattern without needs of permission
        	http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    		http.authorizeRequests().antMatchers("/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("/Admin/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("/css/**").permitAll();
    		http.authorizeRequests().antMatchers("/scripts/**").permitAll();
    		http.authorizeRequests().antMatchers("/images/**").permitAll();
    		http.authorizeRequests().antMatchers("/fonts/**").permitAll();
    		http.authorizeRequests().antMatchers("/Public/**").permitAll();
    		http.authorizeRequests().antMatchers("Upload/**").permitAll();
    		http.authorizeRequests().antMatchers("/").permitAll();
    		// URL pattern with needs of permission
        	http.antMatcher("/Admin/**").authorizeRequests().anyRequest().authenticated().accessDecisionManager(customAccessDecisionManager());
    		// define login pages
    		http.csrf().disable();
    		http.formLogin().loginPage("/Admin/Public/login").loginProcessingUrl("/Admin/j_spring_security_check")
    				.usernameParameter("username").passwordParameter("password").permitAll();
    		http.formLogin().successHandler(authSuccessHandler);
    		http.formLogin().failureHandler(authFailureHandler);
    
    		// define logout pages***
    		http.logout().logoutUrl("/Admin/j_spring_security_logout")
    				.deleteCookies("JSESSIONID").logoutSuccessUrl("/").invalidateHttpSession(true);
    
    		// session manage***
    		http.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
        }
    }
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.headers().frameOptions().sameOrigin();
//
//		// define accessDecisionManager, Specify that URLs are allowed by any
//		// authenticated user
//		http.authorizeRequests().anyRequest().authenticated().accessDecisionManager(customAccessDecisionManager());
//		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);//.accessDeniedPage("/Public/403");//.accessDeniedPage("/Public/error");
//
//		// the resources under the Public do not need permission
//		http.authorizeRequests().antMatchers("/Public/**").permitAll();
//		http.authorizeRequests().antMatchers("/Admin/Public/**").permitAll();
//		http.authorizeRequests().antMatchers("/App/Public/**").permitAll();
//		http.authorizeRequests().antMatchers("/css/**").permitAll();
//		http.authorizeRequests().antMatchers("/scripts/**").permitAll();
//		http.authorizeRequests().antMatchers("/images/**").permitAll();
//		http.authorizeRequests().antMatchers("/fonts/**").permitAll();
//		http.authorizeRequests().antMatchers("/Public/**").permitAll();
//		http.authorizeRequests().antMatchers("Upload/**").permitAll();
//		http.authorizeRequests().antMatchers("/").permitAll();
//
//		// define login pages
//		http.csrf().disable();
//		http.formLogin().loginPage("/Public/login").loginProcessingUrl("/j_spring_security_check")
//				.usernameParameter("username").passwordParameter("password").permitAll();
//		http.formLogin().successHandler(authSuccessHandler);
//		http.formLogin().failureHandler(authFailureHandler);
//
//		// define logout pages***
//		http.logout().logoutUrl("/j_spring_security_logout")
//				.deleteCookies("JSESSIONID").logoutSuccessUrl("/").invalidateHttpSession(true);
//
//		// session manage***
//		http.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
//		
//	}

}
