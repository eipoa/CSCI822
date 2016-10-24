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
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for priority of bugs <br>
 *  <br>
 * CREATE TABLE `bug_priority` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `desc` varchar(45) NOT NULL, <br>
 *   PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8; <br>
 * 
 * @see BugsModel
 */
@Entity
@Table(name = "bug_severity", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BugSeverityModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String severity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	@Override
	public String toString() {
		return "BugSeverityModel [id=" + id + ", severity=" + severity + "]";
	}
	

}
