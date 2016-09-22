/**
 * 
 */
package com.bugtrack.admin.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
