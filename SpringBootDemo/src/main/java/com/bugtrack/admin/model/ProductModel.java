/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for name of products <br>
 *  <br>
 * CREATE TABLE `product` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `name_id` int(11) NOT NULL, <br>
 *   `version` varchar(45) NOT NULL, <br>
 *   `os_id` int(11) NOT NULL, <br>
 *   `status` int(11) NOT NULL DEFAULT '1', <br>
 *   PRIMARY KEY (`id`), <br>
 *   KEY `pd_fk_01` (`os_id`), <br>
 *   KEY `pd_fk_02` (`name_id`), <br>
 *   CONSTRAINT `pd_fk_01` FOREIGN KEY (`os_id`) REFERENCES `product_os` (`id`), <br>
 *   CONSTRAINT `pd_fk_02` FOREIGN KEY (`name_id`) REFERENCES `product_name` (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8; <br>
 * 
 * @see ProductNameModel
 * @see ProductOsModel
 */
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="name_id")
	private ProductNameModel name;

	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="os_id")
	private ProductOsModel os;
	
	@NotNull
	private String version;
	@NotNull
	private int status = 1;	

	public ProductModel() {
		super();
	}
	public ProductNameModel getName() {
		return name;
	}
	public void setName(ProductNameModel name) {
		this.name = name;
	}
	public ProductOsModel getOs() {
		return os;
	}
	public void setOs(ProductOsModel os) {
		this.os = os;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", name=" + name + ", os=" + os + ", version=" + version + ", status="
				+ status + "]";
	}
}
