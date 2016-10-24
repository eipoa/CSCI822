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

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for status of bugs <br>
 *  <br>
 * CREATE TABLE `bug_status` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `desc` varchar(128) NOT NULL, <br>
 *   PRIMARY KEY (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8; <br>
 * 
 * @see BugsModel
 */
@Entity
@Table(name = "bug_comment", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BugCommentModel {
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
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="bugid")
	@JsonManagedReference
	private BugsModel bug;
	public BugsModel getBug() {
		return bug;
	}
	public void setBug(BugsModel bug) {
		this.bug = bug;
	}

	private String thetext;
	public String getThetext() {
		return thetext;
	}
	public void setThetext(String thetext) {
		this.thetext = thetext;
	}
	
	@NotNull
	@Column(name="creation_ts")
	private String creationts;
	public String getCreationts() {
		return creationts;
	}
	public void setCreationts(String creationts) {
		this.creationts = creationts;
	}

	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userid")
	@JsonManagedReference
	private UserModel submitter;
	public UserModel getSubmitter() {
		return submitter;
	}
	public void setSubmitter(UserModel submitter) {
		this.submitter = submitter;
	}

	@NotNull
	private Integer isprivacy = 0;
	public Integer getIsprivacy() {
		return isprivacy;
	}
	public void setIsprivacy(Integer isprivacy) {
		this.isprivacy = isprivacy;
	}
	
//	@OneToMany(cascade = { CascadeType.ALL },mappedBy ="comment")
//	@JsonBackReference
//	private Collection<BugPatchModel> patchs = new ArrayList<BugPatchModel>();
//	public Collection<BugPatchModel> getPatchs() {
//		return patchs;
//	}
//	public void setPatchs(Collection<BugPatchModel> patchs) {
//		this.patchs = patchs;
//	}
//	public void addSolution(BugPatchModel patch){  
//		patch.setComment(this);
//		patchs.add(patch);
//    }
	
}
