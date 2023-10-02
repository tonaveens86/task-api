/**
 * 
 */
package com.test.api.task.entity;

import java.util.Date;

import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.api.task.constants.PriorityEnum;
import com.test.api.task.constants.StatusEnum;
import com.test.api.task.model.ApiRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Product.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 5:54:23 PM (Naveen) Baseline</LI>
 *      </UL>
 * 
 */
@Entity
@Table(name = "T_TASK")
@Setter
@Getter
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="taskId")
public class TaskEntity extends ApiRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TASK_ID")
	private Long taskId;
	
	@NotBlank(message = "20001")
	@Column(name = "TITLE", length = 50, nullable = false, unique = false)
	private String title;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String desc;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private StatusEnum status = StatusEnum.OPEN;
	
	@Column(name = "progress")
	private Integer progress = Integer.valueOf(0);
	
	@Column(name = "USER_ID")
	private Long userId;
	
	// @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private UserEntity user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PRIORITY", nullable = false)
	private PriorityEnum priority = PriorityEnum.LOW;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "COMPLETED_DATE", nullable = true)
	private Date completedDate;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate = new Date();
	
	public void validate(Errors errors) {
		super.validate(errors);
		if(this.completedDate != null) {
			errors.rejectValue("completedDate", "30005", "Completed Date can't be future date.");
		}
	}
}
