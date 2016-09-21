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
	private String name;
//	@NotNull
//	private Integer os_id;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="os_id")
	private OsModel os;
	
	@NotNull
	private String version;
	@NotNull
	private int status = 1;	

	public OsModel getOs() {
		return os;
	}
	public void setOs(OsModel os) {
		this.os = os;
	}
//	public Integer getOs_id() {
//		return os_id;
//	}
//	public void setOs_id(Integer os_id) {
//		this.os_id = os_id;
//	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
