/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugPatchModel;

/**
 * @author Administrator
 *
 */
public interface BugPatchRepository  extends JpaRepository<BugPatchModel, Integer>{

	List<BugPatchModel> findAllByBug_id(Integer id);


	//BugPatchModel findByBug_id(Integer id);

}
