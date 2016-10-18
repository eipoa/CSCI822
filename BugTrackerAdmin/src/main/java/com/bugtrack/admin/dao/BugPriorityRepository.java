/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.BugPriorityModel;

/**
 * @author Administrator
 *
 */
public interface BugPriorityRepository  extends JpaRepository<BugPriorityModel, Integer>{

	List<BugPriorityModel> findAllByOrderByIdAsc();

}
