/**
 * 
 */
package com.bugtrack.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.ProductOsModel;

/**
 * @author Administrator
 *
 */
public interface ProductOsRepository  extends JpaRepository<ProductOsModel, Integer>{

}
