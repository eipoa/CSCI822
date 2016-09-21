/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.VersionModel;

/**
 * @author Administrator
 *
 */
public interface VersionRepository  extends JpaRepository<VersionModel, Integer>{

}
