/**
 * 
 */
package com.springboot.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.springboot.demo.model.UserModel;

/**
 * @author Administrator
 *
 */
public interface UserRepositoryCustom {

	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable);
}
