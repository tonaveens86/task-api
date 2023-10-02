/**
 * 
 */
package com.test.api.task.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 * UserTasksEntity.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 9:52:31 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Entity
@Table(name = "T_USER_TASK")
public class UserTasksEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_TASK_ID")
	private Long userTaskId;
	
	@OneToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
	@OneToOne
	@JoinColumn(name = "TASK_ID")
	private TaskEntity task;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
}
