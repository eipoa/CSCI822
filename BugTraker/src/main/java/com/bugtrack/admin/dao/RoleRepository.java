/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.RoleModel;

/**
 * @author Administrator
 *
 */
public interface RoleRepository  extends JpaRepository<RoleModel, Integer>{

}
