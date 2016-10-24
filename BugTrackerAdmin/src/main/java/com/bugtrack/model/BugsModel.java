/**
 * 
 */
package com.bugtrack.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A entity class for bugs <br>
 *  <br>
 * CREATE TABLE `bug` ( <br>
 *   `id` int(11) NOT NULL AUTO_INCREMENT, <br>
 *   `change_ts` varchar(255) NOT NULL, <br>
 *   `classification_id` int(11) NOT NULL, <br>
 *   `creation_ts` varchar(255) NOT NULL, <br>
 *   `product_id` int(11) NOT NULL, <br>
 *   `rank` int(11) NOT NULL, <br>
 *   `short_desc` varchar(255) NOT NULL, <br>
 *   `title` varchar(255) NOT NULL, <br>
 *   `status_id` int(11) NOT NULL, <br>
 *   `priority_id` int(11) NOT NULL, <br>
 *   `reporter_id` int(11) NOT NULL, <br>
 *   `developer_id` int(11) NOT NULL, <br>
 *   `reviewer_id` int(11) DEFAULT NULL, <br>
 *   PRIMARY KEY (`id`), <br>
 *   KEY `bug_fk_01` (`classification_id`), <br>
 *   KEY `bug_fk_02` (`priority_id`), <br>
 *   KEY `bug_fk_03` (`developer_id`), <br>
 *   KEY `bug_fk_04` (`reporter_id`), <br>
 *   KEY `bug_fk_05` (`reviewer_id`), <br>
 *   KEY `bug_fk_06` (`product_id`), <br>
 *   KEY `bug_fk_07` (`status_id`), <br>
 *   CONSTRAINT `bug_fk_01` FOREIGN KEY (`classification_id`) REFERENCES `bug_class` (`id`), <br>
 *   CONSTRAINT `bug_fk_02` FOREIGN KEY (`priority_id`) REFERENCES `bug_priority` (`id`), <br>
 *   CONSTRAINT `bug_fk_03` FOREIGN KEY (`developer_id`) REFERENCES `user` (`id`), <br>
 *   CONSTRAINT `bug_fk_04` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`id`), <br>
 *   CONSTRAINT `bug_fk_05` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`), <br>
 *   CONSTRAINT `bug_fk_06` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`), <br>
 *   CONSTRAINT `bug_fk_07` FOREIGN KEY (`status_id`) REFERENCES `bug_status` (`id`) <br>
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8; <br>
 * 
 * @see BugClassModel
 * @see BugPriorityModel
 * @see BugStatusModel
 */
@Entity
@Table(name = "bug", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class BugsModel {
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
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="priority_id")// 1:normal/2:medium/3:high
	private BugPriorityModel priority;
	public BugPriorityModel getPriority() {
		return priority;
	}
	public void setPriority(BugPriorityModel priority) {
		this.priority = priority;
	}
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="severity_id")// 1:normal/2:medium/3:high
	private BugSeverityModel severity;
	public BugSeverityModel getSeverity() {
		return severity;
	}
	public void setSeverity(BugSeverityModel severity) {
		this.severity = severity;
	}

	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="classification_id")
	private BugClassModel classification;
	public BugClassModel getClassification() {
		return classification;
	}
	public void setClassification(BugClassModel classification) {
		this.classification = classification;
	}

	/**
	 * @see ProductModel.bugs
	 */
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	@JsonManagedReference
	private ProductModel product;
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="status_id")// 1:new(triager)/2:assigned(developer)/3:verify(reviewer)/4:fixed/5:merged
	private BugStatusModel status;
	public BugStatusModel getStatus() {
		return status;
	}
	public void setStatus(BugStatusModel status) {
		this.status = status;
	}

	@NotNull
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="short_desc")
	private String shortdesc;
	public String getShortdesc() {
		return shortdesc;
	}
	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}
	
	/**
	 * @see UserModel.bugs
	 */
	@NotNull
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="reporter_id")
	@JsonManagedReference
	private UserModel reporter;
	public UserModel getReporter() {
		return reporter;
	}
	public void setReporter(UserModel reporter) {
		this.reporter = reporter;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="developer_id")
	private UserModel developer;
	public UserModel getDeveloper() {
		return developer;
	}
	public void setDeveloper(UserModel developer) {
		this.developer = developer;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="reviewer_id")
	private UserModel reviewer;
	public UserModel getReviewer() {
		return reviewer;
	}
	public void setReviewer(UserModel reviewer) {
		this.reviewer = reviewer;
	}

	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="triager_id")
	private UserModel triager;
	public UserModel getTriager() {
		return triager;
	}
	public void setTriager(UserModel triager) {
		this.triager = triager;
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
	private String change_ts;
	public String getChange_ts() {
		return change_ts;
	}
	public void setChange_ts(String change_ts) {
		this.change_ts = change_ts;
	}

	@NotNull
	private Integer rank = 0;// how many users vote it
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="patch_id")
//	private BugPatchModel solution;
//	public BugPatchModel getSolution() {
//		return solution;
//	}
//	public void setSolution(BugPatchModel solution) {
//		this.solution = solution;
//	}
	
//	@OneToMany(cascade = { CascadeType.ALL },mappedBy ="bug")
//	@JsonBackReference
//	private Collection<BugPatchModel> solutions = new ArrayList<BugPatchModel>();
//	public Collection<BugPatchModel> getSolutions() {
//		return solutions;
//	}
//	public void setSolutions(Collection<BugPatchModel> solutions) {
//		this.solutions = solutions;
//	}
//	public void addSolution(BugPatchModel patch){  
//		patch.setBug(this);
//		solutions.add(patch);
//    } 
//	
	@NotNull
	private Integer vote = 0;
	public Integer getVote() {
		return vote;
	}
	public void setVote(Integer vote) {
		this.vote = vote;
	}
	
	public String getMsg(int s, int e){
		String str = "";
		str = "<h5><span class=\"label label-primary\">Bug ID: " + Integer.toString(this.getId()) + "</span></h5>";
		if(this.getPriority().getId().equals(1))
			str += "<h5><span class=\"label label-info\">Priority: " + this.getPriority().getDescp() + "</span></h5>";
		if(this.getPriority().getId().equals(2))
			str += "<h5><span class=\"label label-warning\">Priority: " + this.getPriority().getDescp() + "</span></h5>";
		if(this.getPriority().getId().equals(3))
			str += "<h5><span class=\"label label-danger\">Priority: " + this.getPriority().getDescp() + "</span></h5>";
		if(s==1 && e==2){
			str += "<h6>A new bug, please give out a solution.</h6>";
		}else if(s==3 && e==2){
			str += "<h6>The solution cannot pass testing, please check again.</h6>";
		}else if(s==2 && e==3){
			str += "<h6>A new solution, please test it.</h6>";
		}
		return str;
	}
	
	@OneToMany(cascade = { CascadeType.ALL },mappedBy ="bug")
	@JsonBackReference
	private Collection<BugCommentModel> comments = new ArrayList<BugCommentModel>();
	public Collection<BugCommentModel> getComments() {
		return comments;
	}
	public void setComments(Collection<BugCommentModel> comments) {
		this.comments = comments;
	}
	public void addSolution(BugCommentModel comment){  
		comment.setBug(this);
		comments.add(comment);
    }

	private String keywords;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	
//	public BugPatchModel getCurentsolution() {
//		BugPatchModel curentSolution = new BugPatchModel();
//		for (BugPatchModel p : solutions) {
//            if(curentSolution.getCreationts()==null||(p.getCreationts().compareTo(curentSolution.getCreationts())>0))
//            	curentSolution = p;
//        }
//		if(curentSolution.getId()!=null)
//			return curentSolution;
//		else
//			return null;
//	}
	
	@Override
	public String toString() {
		return "BugsModel [id=" + id + ", priority=" + priority + ", classification=" + classification + ", product="
				+ product + ", status=" + status + ", title=" + title + ", short_desc=" + shortdesc + ", reporter="
				+ reporter + ", creation_ts=" + creationts + ", developer=" + developer + ", reviewer=" + reviewer
				+ ", change_ts=" + change_ts + ", rank=" + rank + "]";
	}
	
}
