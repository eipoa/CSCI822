/**
 * 
 */
package com.bugtrack.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
	@Column(name="creation_ts")
	private String creationts;
	@Column(name="reply_ts")
	private String replyts;
	
	
	
	
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
	public String getCreationts() {
		return creationts;
	}
	public void setCreationts(String creationts) {
		this.creationts = creationts;
	}
	public String getReplyts() {
		return replyts;
	}
	public void setReplyts(String replyts) {
		this.replyts = replyts;
	}

	@NotNull
	private Integer status = 0;// 0 test 1 release
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="bug_id")
	private BugsModel bug;
	public BugsModel getBug() {
		return bug;
	}
	public void setBug(BugsModel bug) {
		this.bug = bug;
	}
	
//	@NotNull
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="commentid")
//	@JsonManagedReference
//	private BugCommentModel comment;
//	public BugCommentModel getComment() {
//		return comment;
//	}
//	public void setComment(BugCommentModel comment) {
//		this.comment = comment;
//	}
	

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

	
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="submitter_id")
	private UserModel submitter;
	public UserModel getSubmitter() {
		return submitter;
	}
	public void setSubmitter(UserModel submitter) {
		this.submitter = submitter;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="replier_id")
	private UserModel replier;
	public UserModel getReplier() {
		return replier;
	}
	public void setReplier(UserModel replier) {
		this.replier = replier;
	}

}
