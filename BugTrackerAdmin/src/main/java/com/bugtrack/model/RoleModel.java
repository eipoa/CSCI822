/**
 * 
 */
package com.bugtrack.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "auth_role")
public class RoleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	
	@NotNull
	private String rolename;
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}	
	
	
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
	@OneToMany
	private Collection<UserModel> users = new ArrayList<UserModel>();
	public Collection<UserModel> getUsers() {
		return users;
	}
	public void setUsers(Collection<UserModel> users) {
		this.users = users;
	}

	//http://stackoverflow.com/questions/37086074/by-using-jsonbackreference-and-jsonmanagedreference-in-many-to-many-relationsh
	//http://www.cnblogs.com/dzly/archive/2012/03/11/2390194.html
	@ManyToMany(cascade = { CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinTable(
			name="auth_roleresource",
			joinColumns={ @JoinColumn(name="roleid",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="resid",referencedColumnName="id")}
	)
	@JsonManagedReference
	private Collection<ResourceModel> resources = new ArrayList<ResourceModel>();
	public Collection<ResourceModel> getResources() {
		return resources;
	}
	public void setResources(Collection<ResourceModel> resources) {
		this.resources = resources;
	}

	private Integer status=1;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@NotNull
	private Integer fixed = 0;
	public Integer getFixed() {
		return fixed;
	}
	public void setFixed(Integer fixed) {
		this.fixed = fixed;
	}
	
	

//	@Override
//	public String toString() {
//		return "RoleModel [id=" + id + ", rolename=" + rolename + ", description=" + description + "]";
//	}

}
