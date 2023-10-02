/**
 * 
 */
package com.test.api.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.task.entity.TaskEntity;
import com.test.api.task.entity.UserEntity;
import com.test.api.task.model.ApiRequest;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.service.UserServiceI;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

/**
 * 
 * UserController.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 12:40:22 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserController implements BaseRestController {

	@Autowired
	private UserServiceI service;

	/**
	 * Returns the user with give id.
	 * 
	 * @param userId
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/{id}")
	public UserEntity findById(@PathVariable("id") Long id, @Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(id);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findById(sr), serReq, this.service);
	}

	/**
	 * Return all the users.
	 * 
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping
	public List<UserEntity> getAll(@Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findAll(sr), new ServiceRequest<>(),
				this.service);
	}

	/**
	 * Creates new user.
	 * 
	 * @param apiReq
	 * @param reqPayload
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public UserEntity create(@Parameter(hidden=true) ApiRequest apiReq, @RequestBody @Valid UserEntity reqPayload,
			BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);
		var serReq = new ServiceRequest<>(reqPayload);
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.create(sr), serReq, this.service);
	}

	/**
	 * Update given user.
	 * 
	 * @param id
	 * @param apiReq
	 * @param reqPayload
	 * @param bindingResult
	 * @return
	 */
	@PutMapping("/{id}")
	public UserEntity update(@PathVariable("id") Long id, @Parameter(hidden=true) ApiRequest apiReq, @RequestBody @Valid UserEntity reqPayload,
			BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);
		var serReq = new ServiceRequest<>(reqPayload);
		serReq.getParams().put("productId", id.toString());
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.update(sr), serReq, this.service);
	}

	/**
	 * Deletes user by given id.
	 * 
	 * @param id
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@DeleteMapping("/{id}")
	public UserEntity deleteById(@PathVariable("id") Long id, @Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(id);
		return this.invokeService(request, bindingResult, (t, sr) -> t.deleteById(sr), serReq, this.service);
	}

	/**
	 * Returns the user assigned task.
	 * 
	 * @param userId
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/{id}/tasks")
	public List<TaskEntity> getAssignedTasks(@PathVariable("id") Long id, @Parameter(hidden=true) ApiRequest request,
			BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(id);
		return this.invokeService(request, bindingResult, (t, sr) -> t.getAssignedTasks(sr), serReq, this.service);
	}

}
