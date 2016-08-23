/**
 * 
 */
package com.springboot.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.model.UserModel;

/**
 * @author Administrator
 * @reference http://docs.spring.io/spring-data/jpa/docs/1.11.0.M1/reference/html/#repositories
 */
public interface UserRepository extends JpaRepository<UserModel, Integer>{
	
	UserModel findByUsername(String username);

}
