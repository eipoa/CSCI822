/**
 * 
 */
package com.bugtrack.admin.model;

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
	private int age;
	private String email;
	@NotNull
	private int status = 1;
//	@NotNull
//	private String create_ts;
//	@NotNull
//	private String login_ts;

	/**
	 * 1: active
	 * 0: frozen
	 */

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString(){
		return this.username + " - " + this.first_name + " - " + this.last_name 
				+ " - " + this.email + " - " +Integer.toString(this.age) + " - " +Integer.toString(this.status);
	}
}
