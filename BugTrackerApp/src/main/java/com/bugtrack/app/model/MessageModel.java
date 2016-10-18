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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Baoxing Li
 * @version 1.0.0
 */
@Entity
@Table(name = "message")
public class MessageModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fromu")
	private UserModel sender;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tou")
	private UserModel receiver;
	
	@NotNull
	private String title;

	@NotNull
	private String creationts;
	
	private String readts;
	
	@NotNull
	private Integer status=0;
	
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserModel getSender() {
		return sender;
	}
	public void setSender(UserModel sender) {
		this.sender = sender;
	}
	public UserModel getReceiver() {
		return receiver;
	}
	public void setReceiver(UserModel receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreationts() {
		return creationts;
	}
	public void setCreationts(String creationts) {
		this.creationts = creationts;
	}
	public String getReadts() {
		return readts;
	}
	public void setReadts(String readts) {
		this.readts = readts;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MessageModel [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", title=" + title
				+ ", content=" + content + ", creationts=" + creationts + ", readts=" + readts + ", status=" + status + "]";
	}

}
