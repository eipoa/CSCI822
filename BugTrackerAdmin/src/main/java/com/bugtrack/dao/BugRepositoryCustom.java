/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.bugtrack.model.BugStatusModel;

/**
 * @author Administrator
 *
 */
public interface BugRepositoryCustom {
	public List<BugStatusModel> findStatusList(String typeid, String sm);
	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable);
}
