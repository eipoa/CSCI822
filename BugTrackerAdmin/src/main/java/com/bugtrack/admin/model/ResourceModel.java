/**
 * 
 */
package com.bugtrack.admin.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "auth_resource")
public class ResourceModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@Column(unique=true)
	private String resource;
	
	@NotNull
	private String name;
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private Integer status = 1;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@ManyToOne 
	@JoinColumn(name = "parentid")
	@JsonBackReference
	private ResourceModel parent;
	public ResourceModel getParent() {
		return parent;
	}
	public void setParent(ResourceModel parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent",cascade={CascadeType.ALL},fetch=FetchType.LAZY) 
	@JsonManagedReference
	@OrderBy("resource")
	private List<ResourceModel> children;
	public List<ResourceModel> getChildren() {
		return children;
	}
	public void setChilderen(List<ResourceModel> children) {
		this.children = children;
	}

//	public int compareTo(ResourceModel res) {
//		return this.resource.compareTo(res.getResource());
//	}
	
	@ManyToMany(mappedBy="resources")
	@JsonBackReference
	private Collection<RoleModel> roles = new ArrayList<RoleModel>();
	public Collection<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Collection<RoleModel> roles) {
		this.roles = roles;
	}
	public String getRoleString(){
		String str = "need Role [";
		for (RoleModel role : roles) {
			str = str + role.getRolename() + ",";
		}
		str = str + "]";
		return str;
	}


	private Integer tp;// 0 application; 1 module; 2 button; 3 function
	public Integer getTp() {
		return tp;
	}
	public void setTp(Integer tp) {
		this.tp = tp;
	}

}
