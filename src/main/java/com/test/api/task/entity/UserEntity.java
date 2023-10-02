/**
 * 
 */
package com.test.api.task.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.api.task.model.ApiRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * ApprovalQueue.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 12:00:12 AM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@Table(name = "T_USERS")
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="userId")
public class UserEntity extends ApiRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long userId;
	
	@NotBlank(message = "10001")
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@NotBlank(message = "10002")
	@Column(name = "LAST_NAME")
	private String lastName;

	// @JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<TaskEntity> tasks;

	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();
}
