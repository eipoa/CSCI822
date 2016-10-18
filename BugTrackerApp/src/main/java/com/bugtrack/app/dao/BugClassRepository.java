/**
 * 
 */
package com.bugtrack.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.BugClassModel;

/**
 * @author Administrator
 *
 */
public interface BugClassRepository  extends JpaRepository<BugClassModel, Integer>{

	List<BugClassModel> findAllByOrderByIdAsc();

}
