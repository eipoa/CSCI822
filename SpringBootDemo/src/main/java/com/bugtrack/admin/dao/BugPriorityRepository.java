/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.BugPriorityModel;

/**
 * @author Administrator
 *
 */
public interface BugPriorityRepository  extends JpaRepository<BugPriorityModel, Integer>{

}
