/**
 * 
 */
package com.bugtrack.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugCommentModel;
import com.bugtrack.model.BugsModel;

/**
 * @author Administrator
 *
 */
public interface BugCommentRepository  extends JpaRepository<BugCommentModel, Integer>{

	//Page<BugCommentModel> findAllByBugid(Integer id, Pageable pageable);

	Page<BugCommentModel> findAllByBug(BugsModel findOne, Pageable pageable);

	BugCommentModel findOneByBug(BugsModel findOne);

}
