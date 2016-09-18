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
 * @author Administrator
 *
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

}
