/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.BugStatusModel;

/**
 * @author Administrator
 *
 */
public interface BugStatusRepository  extends JpaRepository<BugStatusModel, Integer>{


}
