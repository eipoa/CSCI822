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
@Table(name = "version", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class VersionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private Integer pid;
	@NotNull
	private Integer oid;
	@NotNull
	private String versiond;
	@NotNull
	private int status = 1;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getVersiond() {
		return versiond;
	}
	public void setVersiond(String versiond) {
		this.versiond = versiond;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
