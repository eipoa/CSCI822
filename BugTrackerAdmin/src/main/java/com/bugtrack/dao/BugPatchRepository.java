/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugPatchModel;
import com.bugtrack.model.BugsModel;

/**
 * @author Administrator
 *
 */
public interface BugPatchRepository  extends JpaRepository<BugPatchModel, Integer>{

	List<BugPatchModel> findAllByBugOrderByCreationts(BugsModel findOne);

	BugPatchModel findTop1ByBugOrderByCreationtsDesc(BugsModel bug);

	BugPatchModel findOneByBugAndStatus(BugsModel bug, int i);


}
