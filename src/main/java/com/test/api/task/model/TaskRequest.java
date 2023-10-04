/**
 * 
 */
package com.test.api.task.model;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.test.api.task.constants.StatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * AssignTaskRequest.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 10:29:17 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Setter
@Getter
public class TaskRequest extends ApiRequest {

	@NotNull(message = "30001")
	private Long taskId;
	private Long userId;
	
	private StatusEnum status;
	
	private Integer progress;
	
	private Date startDate;
	
	private Date endDate;
	
	@Override
	public void validate(Errors errors) {
		super.validate(errors);
	}

	/**
	 * @param errors
	 */
	public void validateAssignTask(Errors errors) {
		super.validate(errors);
		ValidationUtils.rejectIfEmpty(errors, "userId", "30002", "UserId can't be null or empty");
	}
	
	public void validateCompletedTaskRequest(Errors errors) {
		super.validate(errors);
		ValidationUtils.rejectIfEmpty(errors, "startDate", "30003", "startDate can't be null");
		ValidationUtils.rejectIfEmpty(errors, "endDate", "30004", "endDate can't be null");
	}
	
}
