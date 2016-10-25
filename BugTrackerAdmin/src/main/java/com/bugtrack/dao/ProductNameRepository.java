/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bugtrack.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
public interface ProductNameRepository  extends JpaRepository<ProductNameModel, Integer>{

	@Query(value="select n.* from product_name n where n.id in (select p.name_id from product p); ", nativeQuery = true)
	List<ProductNameModel> findProductList();
}
