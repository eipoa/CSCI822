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
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for name of products <br>
 *  <br>
 * CREATE TABLE `product_name` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `name` varchar(45) NOT NULL, <br>
 *   PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8; <br>
 * 
 * @see ProductModel
 */
@Entity
@Table(name = "product_name", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ProductNameModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String name;

	public ProductNameModel() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ProductNameModel [id=" + id + ", name=" + name + "]";
	}
}
