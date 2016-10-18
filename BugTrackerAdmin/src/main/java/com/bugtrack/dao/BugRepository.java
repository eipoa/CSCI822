/**
 * 
 */
package com.bugtrack.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.BugsModel;

/**
 * @author Administrator
 * @reference http://docs.spring.io/spring-data/jpa/docs/1.11.0.M1/reference/html/#repositories
 */
public interface BugRepository extends JpaRepository<BugsModel, Integer>, BugRepositoryCustom{
	public Page<BugsModel> findAll(Pageable pageable);
	public BugsModel findById(Integer id);
}
