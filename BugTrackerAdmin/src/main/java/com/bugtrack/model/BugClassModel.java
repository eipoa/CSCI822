/**
 * 
 */
package com.bugtrack.model;

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
@Table(name = "bug_class", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BugClassModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String descp;
	public Integer getId() {
		return id;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String desc) {
		this.descp = desc;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
