/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
@Entity
//@SqlResultSetMapping(name = "BugsMapping", entities = { 
//		@EntityResult(entityClass = BugsModel.class, fields = {
//			@FieldResult(name = "id", column = "id"), 
//			@FieldResult(name = "priority", column = "priority"),
//			@FieldResult(name = "title", column = "title"), 
//			@FieldResult(name = "short_desc", column = "short_desc"),
//			@FieldResult(name = "classification_id", column = "classification_id"),
//			@FieldResult(name = "product_id", column = "product_id"),
//			@FieldResult(name = "version_id", column = "version_id"), 
//			@FieldResult(name = "os_id", column = "os_id"),
//			@FieldResult(name = "bug_status", column = "bug_status"), 
//			@FieldResult(name = "reporter", column = "reporter"),
//			@FieldResult(name = "creation_ts", column = "creation_ts"),
//			@FieldResult(name = "developer", column = "developer"), 
//			@FieldResult(name = "reviewer", column = "reviewer"),
//			@FieldResult(name = "change_ts", column = "change_ts"), 
//			@FieldResult(name = "rank", column = "rank") }),
//		@EntityResult(entityClass = OsModel.class, fields = {
//				@FieldResult(name = "id", column = "oid"),
//				@FieldResult(name = "osname", column = "osname")
//				}) })
@SqlResultSetMappings({
	  @SqlResultSetMapping(
	      name="BugsMapping",
	      entities={@EntityResult(entityClass=BugsModel.class),
	                @EntityResult(entityClass=ProductModel.class),
	                @EntityResult(entityClass=OsModel.class)}
	  )
})
@Table(name = "bug", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class BugsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="priority_id")// 1:normal/2:medium/3:high
	private BugPriorityModel priority;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="classification_id")
	private BugClassModel classification;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	private ProductModel product;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="status_id")// 1:new(triager)/2:assigned(developer)/3:verify(reviewer)/4:fixed/5:merged
	private BugStatusModel status;
	
	@NotNull
	private String title;
	@NotNull
	private String short_desc;
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="reporter_id")
	private UserModel reporter;
	
	@NotNull
	private String creation_ts;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="developer_id")
	private UserModel developer;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="reviewer_id")
	private UserModel reviewer;
	
	
	@NotNull
	private String change_ts;
	@NotNull
	private Integer rank = 0;// how many users vote it

	
	public UserModel getReporter() {
		return reporter;
	}

	public void setReporter(UserModel reporter) {
		this.reporter = reporter;
	}

	public UserModel getDeveloper() {
		return developer;
	}

	public void setDeveloper(UserModel developer) {
		this.developer = developer;
	}

	public UserModel getReviewer() {
		return reviewer;
	}

	public void setReviewer(UserModel reviewer) {
		this.reviewer = reviewer;
	}

	public BugPriorityModel getPriority() {
		return priority;
	}

	public void setPriority(BugPriorityModel priority) {
		this.priority = priority;
	}

	public BugClassModel getClassification() {
		return classification;
	}

	public void setClassification(BugClassModel classification) {
		this.classification = classification;
	}

	public BugStatusModel getStatus() {
		return status;
	}

	public void setStatus(BugStatusModel status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_desc() {
		return short_desc;
	}

	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}

	public ProductModel getProduct() {
		return product;
	}

	public void setProduct(ProductModel product) {
		this.product = product;
	}

	public String getCreation_ts() {
		return creation_ts;
	}

	public void setCreation_ts(String creation_ts) {
		this.creation_ts = creation_ts;
	}

	public String getChange_ts() {
		return change_ts;
	}

	public void setChange_ts(String change_ts) {
		this.change_ts = change_ts;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
