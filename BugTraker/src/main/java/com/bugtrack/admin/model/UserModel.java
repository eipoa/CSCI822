/**
 * 
 */
package com.bugtrack.admin.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for user of bugs system <br>
 *  <br>
 * CREATE TABLE `user` ( <br>
 *  `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *  `username` varchar(45) NOT NULL, <br>
 *  `password` varchar(45) NOT NULL, <br>
 *  `first_name` varchar(45) NOT NULL, <br>
 *  `last_name` varchar(45) NOT NULL, <br>
 *  `age` int(11) DEFAULT NULL, <br>
 *  `email` varchar(45) DEFAULT NULL, <br>
 *  `create_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, <br>
 *  `reputation` int(11) NOT NULL, <br>
 *  `login_ts` varchar(45) DEFAULT NULL, <br>
 *  `status` int(11) NOT NULL DEFAULT '1', <br>
 *  `role_id` int(11) NOT NULL, <br>
 *  PRIMARY KEY (`id`), <br>
 *  UNIQUE KEY `user_uni_01` (`username`), <br>
 *  KEY `user_fk_01` (`role_id`), <br>
 *  CONSTRAINT `user_fk_01` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='table for registered user'; <br>
 * http://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
 *  * @see RoleModel
 */
@Entity
@Table(name = "auth_user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@NotNull
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		// MD5
		this.password = password;
	}
	
	@NotNull
	private String first_name;
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	@NotNull
	private String last_name;
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	private Integer age;
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
	@NotNull
	private Integer status = 1;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)//
	@JoinTable(name="auth_roleuser",
    joinColumns={ @JoinColumn(name="userid",referencedColumnName="id")},
    inverseJoinColumns={@JoinColumn(name="roleid",referencedColumnName="id")})
	private Collection<RoleModel> roles = new ArrayList<RoleModel>();
	public Collection<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Collection<RoleModel> roles) {
		this.roles = roles;
	}
	public String getRoleString(){
		String str = "has role [";
		for (RoleModel role : roles) {
			str = str + role.getRolename() + ",";
		}
		str = str + "]";
		return str;
	}
	public boolean isAdmin(){
		for (RoleModel r : roles) {
			if(r.getRolename().equals("ROLE_ADMIN")){
				return true;
			}
		}
		return false;
	}
	/**
	 * @see BugsModel.reporter
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="reporter")
	@JsonBackReference
	private Collection<BugsModel> bugs = new ArrayList<BugsModel>();
	public Collection<BugsModel> getBugs() {
		return bugs;
	}
	public void setBugs(Collection<BugsModel> bugs) {
		this.bugs = bugs;
	}

	@NotNull
	private Integer reputation = 0;
	public Integer getReputation() {
		return reputation;
	}
	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}

	@NotNull
	private String create_ts;
	public String getCreate_ts() {
		return create_ts;
	}
	public void setCreate_ts(String create_ts) {
		this.create_ts = create_ts;
	}

	private String login_ts;
	public String getLogin_ts() {
		return login_ts;
	}
	public void setLogin_ts(String login_ts) {
		this.login_ts = login_ts;
	}
	
	private String skill;
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	@NotNull
	private Integer fixed = 0;
	public Integer getFixed() {
		return fixed;
	}
	public void setFixed(Integer fixed) {
		this.fixed = fixed;
	}
	
	
}
