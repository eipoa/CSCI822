/**
 * 
 */
package com.bugtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
public interface ProductNameRepository  extends JpaRepository<ProductNameModel, Integer>{

}
