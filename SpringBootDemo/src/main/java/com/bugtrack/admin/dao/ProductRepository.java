/**
 * 
 */
package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.ProductModel;

/**
 * @author Administrator
 *
 */
public interface ProductRepository  extends JpaRepository<ProductModel, Integer>{

}
