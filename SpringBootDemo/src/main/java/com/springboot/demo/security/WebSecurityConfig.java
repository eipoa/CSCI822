/**
 * 
 */
package com.springboot.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		web.ignoring().antMatchers("/css/**").antMatchers("/scripts/**").antMatchers("/images/**")
				.antMatchers("/data/**").antMatchers("/Public/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 自定义accessDecisionManager访问控制器,并开启表达式语言
		http.authorizeRequests().anyRequest().authenticated();
		
		// 
		//http.authorizeRequests().antMatchers("/Public/**").permitAll();
		
		// 自定义登录页面
		http.csrf().disable().formLogin().loginPage("/Public/login").loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/").permitAll();

		// 自定义注销
		http.logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login").invalidateHttpSession(true);

		// session管理
		http.sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
	}

	// user userDetailsService to Authenticate
	// but what is Authentication ? check password?? or some else
	// the next step is to check the role (accessDecisionManager)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(new ProjectUserDetailService());// .passwordEncoder(new Md5PasswordEncoder());
		auth.inMemoryAuthentication().withUser("123").password("123").roles("USER");
	}
}
