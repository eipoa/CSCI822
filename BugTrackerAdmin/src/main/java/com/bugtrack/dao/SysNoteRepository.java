/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.SysNoteModel;

/**
 * @author Administrator
 *
 */
public interface SysNoteRepository  extends JpaRepository<SysNoteModel, Integer>{

	List<SysNoteModel> findAllByOrderByExpireDesc();

	List<SysNoteModel> findTop3ByExpireGreaterThan(String currentTime);


}
