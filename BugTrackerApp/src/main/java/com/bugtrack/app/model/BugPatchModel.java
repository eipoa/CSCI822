/**
 * 
 */
package com.bugtrack.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "bug_patch", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BugPatchModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String description;
	private String url;
	private String creation_ts;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreation_ts() {
		return creation_ts;
	}
	public void setCreation_ts(String creation_ts) {
		this.creation_ts = creation_ts;
	}
	
	@NotNull
	private Integer status = 0;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="bug_id")
	@JsonManagedReference
	private BugsModel bug;
	public BugsModel getBug() {
		return bug;
	}
	public void setBug(BugsModel bug) {
		this.bug = bug;
	}
	
	private String reply = "";
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
//	public BugPatchModel() {
//		super();
//		status = 0;
//	}
//	public BugPatchModel(String description, String url, String creation_ts, Integer status, BugsModel bug) {
//		super();
//		this.description = description;
//		this.url = url;
//		this.creation_ts = creation_ts;
//		this.status = status;
//		this.bug = bug;
//	}

	
//	@NotNull
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="bug_id")
//	private BugsModel bug;
//	public BugsModel getBug() {
//		return bug;
//	}
//	public void setBug(BugsModel bug) {
//		this.bug = bug;
//	}

}
