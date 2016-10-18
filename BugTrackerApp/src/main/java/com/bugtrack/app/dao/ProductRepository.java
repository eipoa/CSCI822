/**
 * 
 */
package com.bugtrack.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.ProductModel;
import com.bugtrack.app.model.ProductNameModel;
import com.bugtrack.app.model.ProductOsModel;

/**
 * @author Administrator
 *
 */
public interface ProductRepository  extends JpaRepository<ProductModel, Integer>{

	List<ProductModel> findByName(ProductNameModel name);

	List<ProductModel> findByNameAndVersion(ProductNameModel nameById, String ver);

	ProductModel findByNameAndVersionAndOs(ProductNameModel nameById, String ver, ProductOsModel osById);

}
