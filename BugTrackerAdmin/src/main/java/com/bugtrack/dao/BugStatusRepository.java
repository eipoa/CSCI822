/**
 * 
 */
package com.bugtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugStatusModel;

/**
 * @author Administrator
 *
 */
public interface BugStatusRepository  extends JpaRepository<BugStatusModel, Integer>{

	BugStatusModel findByDesc(String string);


}
