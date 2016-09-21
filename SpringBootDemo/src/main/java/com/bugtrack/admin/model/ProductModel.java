/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String productname;
	@NotNull
	private int status = 1;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productname;
	}
	public void setProductName(String productname) {
		this.productname = productname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
