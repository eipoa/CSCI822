/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.BugClassModel;

/**
 * @author Administrator
 *
 */
public interface BugClassRepository  extends JpaRepository<BugClassModel, Integer>{

	List<BugClassModel> findAllByOrderByIdAsc();

}
