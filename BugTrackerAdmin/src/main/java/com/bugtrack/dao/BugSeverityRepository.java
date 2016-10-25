/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugSeverityModel;

/**
 * @author Administrator
 *
 */
public interface BugSeverityRepository  extends JpaRepository<BugSeverityModel, Integer>{

	List<BugSeverityModel> findAllByOrderByIdAsc();

}
