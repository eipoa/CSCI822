/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.BugPatchModel;

/**
 * @author Administrator
 *
 */
public interface BugPatchRepository  extends JpaRepository<BugPatchModel, Integer>{

	//BugPatchModel findByBug_id(Integer id);

}
