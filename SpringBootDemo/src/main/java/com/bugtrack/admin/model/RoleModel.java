/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for role of bugs system <br>
 *  <br>
 * CREATE TABLE `role` ( <br>
 *  `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *  `rolename` varchar(45) NOT NULL, <br>
 *  `description` varchar(255) DEFAULT NULL, <br>
 *  PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8; <br>
 * 
 * @see UserModel
 */
@Entity
@Table(name = "role")
public class RoleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String rolename;
	private String description;
	
	public int getId() {
		return id;
	}

	public RoleModel(){
		
	}
	public RoleModel(int _id, String _rolename, String _desc){
		this.id= _id;
		this.rolename= _rolename;
		this.description= _desc;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return rolename;
	}

	public void setRoleName(String roleName) {
		this.rolename = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RoleModel [id=" + id + ", rolename=" + rolename + ", description=" + description + "]";
	}

}
