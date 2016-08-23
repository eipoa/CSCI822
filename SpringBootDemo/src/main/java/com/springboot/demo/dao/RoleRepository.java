/**
 * 
 */
package com.springboot.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.model.RoleModel;

/**
 * @author Administrator
 *
 */
public interface RoleRepository  extends JpaRepository<RoleModel, Integer>{

}
