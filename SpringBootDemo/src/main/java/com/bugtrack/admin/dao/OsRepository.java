/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.OsModel;

/**
 * @author Administrator
 *
 */
public interface OsRepository  extends JpaRepository<OsModel, Integer>{

}
