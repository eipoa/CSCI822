/**
 * 
 */
package com.bugtrack.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
public interface ProductNameRepository  extends JpaRepository<ProductNameModel, Integer>{

}
