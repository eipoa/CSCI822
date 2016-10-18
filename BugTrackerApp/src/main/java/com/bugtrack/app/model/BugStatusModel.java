/**
 * 
 */
package com.bugtrack.app.model;

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
 * A entity class for status of bugs <br>
 *  <br>
 * CREATE TABLE `bug_status` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `desc` varchar(128) NOT NULL, <br>
 *   PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8; <br>
 * 
 * @see BugsModel
 */
@Entity
@Table(name = "bug_status", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BugStatusModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String desc;
	
	public BugStatusModel() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "BugStatusModel [id=" + id + ", desc=" + desc + "]";
	}
}
