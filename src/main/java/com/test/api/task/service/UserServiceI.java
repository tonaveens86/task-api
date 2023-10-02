/**
 * 
 */
package com.test.api.task.service;

import java.util.List;

import com.test.api.task.entity.TaskEntity;
import com.test.api.task.entity.UserEntity;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.model.ServiceResponse;

/**
 * 
 * UserServiceI.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 12:07:17 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
public interface UserServiceI {

	/**
	 * Creates a User in database and returns it.
	 * 
	 * @param req
	 * @return - User object
	 */
	public ServiceResponse<UserEntity> create(ServiceRequest<UserEntity> req);
	
	/**
	 * Updates a User in database and returns it.
	 * 
	 * @param req - User object wrapper
	 * @return - Updated User object
	 */
	public ServiceResponse<UserEntity> update(ServiceRequest<UserEntity> req);
	
	/**
	 * Deletes a User with give User id
	 * 
	 * @param req - wrapper object for User id
	 * @return - Deleted User id
	 */
	public ServiceResponse<UserEntity> deleteById(ServiceRequest<Long> req);
	
	/**
	 * Returns the User from DB with given User id.
	 * 
	 * @param req - wrapper object contains User id.
	 * @return - User object
	 */
	public ServiceResponse<UserEntity> findById(ServiceRequest<Long> req);

	/**
	 * Returns all the Users from DB
	 * 
	 * @param req - wrapper object
	 * @return - Users list
	 */
	public ServiceResponse<List<UserEntity>> findAll(ServiceRequest<?> req);
	
	/**
	 * Returns the user assigned tasks from DB with given User id.
	 * 
	 * @param req - wrapper object contains User id.
	 * @return - User object
	 */
	public ServiceResponse<List<TaskEntity>> getAssignedTasks(ServiceRequest<Long> req);
	
}
