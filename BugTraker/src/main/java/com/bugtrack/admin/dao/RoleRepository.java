/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.RoleModel;

/**
 * @author Administrator
 *
 */
public interface RoleRepository  extends JpaRepository<RoleModel, Integer>{

	List<RoleModel> findAllByStatus(int i, Sort sort);

	RoleModel findByRolename(String string);

}
