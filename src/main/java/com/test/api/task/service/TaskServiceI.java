/**
 * 
 */
package com.test.api.task.service;

import java.util.List;

import com.test.api.task.entity.TaskEntity;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.model.ServiceResponse;
import com.test.api.task.model.TaskMetrics;
import com.test.api.task.model.TaskRequest;

/**
 * TaskServiceI.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 11:00:26 AM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
public interface TaskServiceI {

	/**
	 * Creates a Task in database and returns it.
	 * 
	 * @param req
	 * @return - Task object
	 */
	public ServiceResponse<TaskEntity> create(ServiceRequest<TaskEntity> req);

	/**
	 * Updates a Task in database and returns it.
	 * 
	 * @param req - Task object wrapper
	 * @return - Updated task object
	 */
	public ServiceResponse<TaskEntity> update(ServiceRequest<TaskEntity> req);

	/**
	 * Deletes a Task with give task id
	 * 
	 * @param req - wrapper object for task id
	 * @return - Deleted task id
	 */
	public ServiceResponse<TaskEntity> deleteById(ServiceRequest<Long> req);

	/**
	 * Returns the Task from DB with given task id.
	 * 
	 * @param req - wrapper object contains task id.
	 * @return - task object
	 */
	public ServiceResponse<TaskEntity> findById(ServiceRequest<Long> req);

	/**
	 * Returns all the Tasks from DB
	 * 
	 * @param req - wrapper object
	 * @return - tasks list
	 */
	public ServiceResponse<List<TaskEntity>> findAll(ServiceRequest<?> req);

	/**
	 * Assigns the given Task to given User
	 * 
	 * @param req - wrapper object contains task and user.
	 * @return - assigned task object
	 */
	public ServiceResponse<TaskEntity> assignTask(ServiceRequest<TaskRequest> req);

	/**
	 * Updates the Task status in DB
	 * 
	 * @param req - wrapper object contains task and status.
	 * @return - updated task object
	 */
	public ServiceResponse<TaskEntity> updateTaskProgress(ServiceRequest<TaskRequest> req);

	/**
	 * Returns Overdue tasks from DB.
	 * 
	 * @param req - wrapper object
	 * @return - list of overdue tasks
	 */
	public ServiceResponse<List<TaskEntity>> getOverdueTasks(ServiceRequest<?> req);

	/**
	 * Returns tasks with given status.
	 * 
	 * @param req - wrapper object contains task status
	 * @return - list of tasks
	 */
	public ServiceResponse<List<TaskEntity>> getTasksByStatus(ServiceRequest<String> req);

	/**
	 * Returns completed tasks in give date range.
	 * 
	 * @param req - wrapper object contains date range (startDate and endDate)
	 * @return - list of tasks
	 */
	public ServiceResponse<List<TaskEntity>> getCompletedTaskWithDataRange(ServiceRequest<TaskRequest> req);

	/**
	 * Return Task statistics
	 * 
	 * @param req - wrapper object
	 * @return - task metrics
	 */
	public ServiceResponse<TaskMetrics> getTaskMetrics(ServiceRequest<?> req);
}
