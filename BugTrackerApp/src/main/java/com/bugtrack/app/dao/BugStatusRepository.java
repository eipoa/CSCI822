/**
 * 
 */
package com.bugtrack.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.BugStatusModel;

/**
 * @author Administrator
 *
 */
public interface BugStatusRepository  extends JpaRepository<BugStatusModel, Integer>{


}
