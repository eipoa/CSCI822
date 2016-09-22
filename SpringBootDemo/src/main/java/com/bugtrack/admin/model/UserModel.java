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
 * 
 *  * @see RoleModel
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String first_name;
	@NotNull
	private String last_name;
	private Integer age;
	private String email;
	@NotNull
	private Integer status = 1;
	@NotNull
	private String create_ts;
	private String login_ts;
	@NotNull
	private Integer reputation = 0;
	/**
	 * @see RoleModel
	 */
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="role_id")
	private RoleModel role;

	public UserModel() {
		super();
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public String getCreate_ts() {
		return create_ts;
	}

	public void setCreate_ts(String create_ts) {
		this.create_ts = create_ts;
	}

	public String getLogin_ts() {
		return login_ts;
	}

	public void setLogin_ts(String login_ts) {
		this.login_ts = login_ts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", username=" + username + ", password=" + password + ", first_name="
				+ first_name + ", last_name=" + last_name + ", age=" + age + ", email=" + email + ", status=" + status
				+ ", create_ts=" + create_ts + ", login_ts=" + login_ts + ", role=" + role + "]";
	}
	
}
