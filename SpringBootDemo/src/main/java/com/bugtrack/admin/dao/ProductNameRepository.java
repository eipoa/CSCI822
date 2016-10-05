/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
public interface ProductNameRepository  extends JpaRepository<ProductNameModel, Integer>{

}
