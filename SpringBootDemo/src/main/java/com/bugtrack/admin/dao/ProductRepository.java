/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.ProductModel;
import com.bugtrack.admin.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
public interface ProductRepository  extends JpaRepository<ProductModel, Integer>{

	List<ProductModel> findByName(ProductNameModel name);

	List<ProductModel> findByNameAndVersion(ProductNameModel nameById, String ver);



}
