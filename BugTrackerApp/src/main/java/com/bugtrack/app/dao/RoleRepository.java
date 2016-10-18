/**
 * 
 */
package com.bugtrack.app.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.RoleModel;

/**
 * @author Administrator
 *
 */
public interface RoleRepository  extends JpaRepository<RoleModel, Integer>{

	List<RoleModel> findAllByStatus(int i, Sort sort);

	RoleModel findByRolename(String string);

	Collection<RoleModel> findAllById(int i);

}
