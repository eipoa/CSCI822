/**
 * 
 */
package com.bugtrack.dao;

import java.util.Map;

import org.springframework.data.domain.Pageable;

/**
 * @author Administrator
 *
 */
public interface UserRepositoryCustom {

	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable);
}
