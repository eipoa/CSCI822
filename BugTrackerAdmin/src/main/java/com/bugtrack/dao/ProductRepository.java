/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;

/**
 * @author Administrator
 *
 */
public interface ProductRepository  extends JpaRepository<ProductModel, Integer>{

	List<ProductModel> findByName(ProductNameModel name);

	List<ProductModel> findByNameAndVersion(ProductNameModel nameById, String ver);

	ProductModel findByNameAndVersionAndOs(ProductNameModel nameById, String ver, ProductOsModel osById);

	List<ProductModel> findAllByName_idAndVersion(Integer id, String ver);

	ProductModel findTop1ByNameAndVersion(ProductNameModel pname, String product_version);

	List<ProductModel> findAllByName(ProductNameModel findOne);

}
