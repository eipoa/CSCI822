/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	                @EntityResult(entityClass=OsModel.class),
	                @EntityResult(entityClass=VersionModel.class)}
	  )
})
@Table(name = "bug", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class BugsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private Integer priority;// 1:normal/2:medium/3:high
	@NotNull
	private String title;
	@NotNull
	private String short_desc;
	@NotNull
	private Integer classification_id;
	@NotNull
	private Integer product_id;
	@NotNull
	private Integer version_id;
	@NotNull
	private String os_id;
	@NotNull
	private Integer bug_status = 1;// 1:new(triager)/2:assigned(developer)/3:verify(reviewer)/4:close
	@NotNull
	private String reporter;
	@NotNull
	private String creation_ts;
	private String developer;
	private String reviewer;
	@NotNull
	private String change_ts;// staffs or reporter(before assigned) can modify
								// the report,
	@NotNull
	private Integer rank = 0;

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

	public Integer getClassification_id() {
		return classification_id;
	}

	public void setClassification_id(Integer classification_id) {
		this.classification_id = classification_id;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getBug_status() {
		return bug_status;
	}

	public void setBug_status(Integer bug_status) {
		this.bug_status = bug_status;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getCreation_ts() {
		return creation_ts;
	}

	public void setCreation_ts(String creation_ts) {
		this.creation_ts = creation_ts;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getVersion_id() {
		return version_id;
	}

	public void setVersion_id(Integer version_id) {
		this.version_id = version_id;
	}

	public String getOs_id() {
		return os_id;
	}

	public void setOs_id(String os_id) {
		this.os_id = os_id;
	}
}
