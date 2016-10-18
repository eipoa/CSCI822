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
 * A entity class for operating system of products <br>
 *  <br>
 * CREATE TABLE `product_os` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `osname` varchar(45) NOT NULL, <br>
 *   PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8; <br>
 * 
 * @see ProductModel
 */
@Entity
@Table(name = "product_os", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ProductOsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String osname;
	
	public ProductOsModel() {
		super();
	}
	public ProductOsModel(String osname) {
		super();
		this.osname = osname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname(String osname) {
		this.osname = osname;
	}
	@Override
	public String toString() {
		return "ProductOsModel [id=" + id + ", osname=" + osname + "]";
	}
}
