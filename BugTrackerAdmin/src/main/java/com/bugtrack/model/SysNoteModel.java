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
 * @author Administrator
 *
 */
@Entity
@Table(name = "sysnote", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class SysNoteModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String descp;
	public Integer getId() {
		return id;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String desc) {
		this.descp = desc;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@NotNull
	private String start;
	@NotNull
	private String expire;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getExpire() {
		return expire;
	}
	public void setExpire(String expire) {
		this.expire = expire;
	}
	
}
