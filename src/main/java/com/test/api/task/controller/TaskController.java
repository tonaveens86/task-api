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
import com.test.api.task.model.ApiRequest;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.model.TaskMetrics;
import com.test.api.task.model.TaskRequest;
import com.test.api.task.service.TaskServiceI;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

/**
 * TaskController.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *          <BR>
 *          <B> Revision History: </B>
 *          <UL>
 *          <LI>Sep 30, 2023 12:40:11 PM (Naveen) Baseline</LI>
 *          </UL>
 *
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController implements BaseRestController {

	@Autowired
	private TaskServiceI service;

	/**
	 * Returns the Task from DB with given task id.
	 * 
	 * @param taskId
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/{taskId}")
	public TaskEntity findById(@PathVariable("taskId") Long taskId, @Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(taskId);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findById(sr), serReq, this.service);
	}

	/**
	 * Returns all the Tasks from DB
	 * 
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping
	public List<TaskEntity> getAll(@Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		return this.invokeService(request, bindingResult, (t, sr) -> t.findAll(sr), new ServiceRequest<>(),
				this.service);
	}

	/**
	 * Creates a Task in database and returns it.
	 * 
	 * @param apiReq
	 * @param reqPayload
	 * @param bindingResult
	 * @return
	 */
	@PostMapping
	public TaskEntity create(@Parameter(hidden=true) ApiRequest apiReq, @RequestBody @Valid TaskEntity reqPayload,
			BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);
		var serReq = new ServiceRequest<>(reqPayload);
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.create(sr), serReq, this.service);
	}

	/**
	 * Updates a Task in database and returns it.
	 * 
	 * @param taskId
	 * @param apiReq
	 * @param reqPayload
	 * @param bindingResult
	 * @return
	 */
	@PutMapping("/{taskId}")
	public TaskEntity update(@PathVariable("taskId") Long taskId, @Parameter(hidden=true) ApiRequest apiReq,
			@RequestBody @Valid TaskEntity reqPayload, BindingResult bindingResult) {
		reqPayload.bindProps(apiReq);
		reqPayload.validate(bindingResult);

		var serReq = new ServiceRequest<>(reqPayload);
		serReq.getParams().put("productId", taskId.toString());
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.update(sr), serReq, this.service);
	}

	/**
	 * Deletes a Task with give task id
	 * 
	 * @param taskId
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@DeleteMapping("/{taskId}")
	public TaskEntity deleteById(@PathVariable("taskId") Long taskId, @Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(taskId);
		return this.invokeService(request, bindingResult, (t, sr) -> t.deleteById(sr), serReq, this.service);
	}

	/**
	 * Assigns the given Task to given User
	 * 
	 * @param apiReq
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/{taskId}/assign")
	public TaskEntity assignTask(@Parameter(hidden=true) ApiRequest apiReq, @RequestBody @Valid TaskRequest request,
			BindingResult bindingResult) {
		request.bindProps(apiReq);
		request.validateAssignTask(bindingResult);
		var serReq = new ServiceRequest<>(request);
		return this.invokeService(request, bindingResult, (t, sr) -> t.assignTask(sr), serReq, this.service);
	}

	/**
	 * Updates the Task status in DB
	 * 
	 * @param req - wrapper object contains task and status.
	 * @return - updated task object
	 */
	@PutMapping("/{taskId}/progress")
	public TaskEntity updateTaskProgress(@Parameter(hidden=true) ApiRequest apiReq, @RequestBody @Valid TaskRequest request,
			BindingResult bindingResult) {
		request.bindProps(apiReq);
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(request);
		return this.invokeService(request, bindingResult, (t, sr) -> t.updateTaskProgress(sr), serReq, this.service);
	}

	/**
	 * Returns Overdue tasks from DB.
	 * 
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/overdue")
	public List<TaskEntity> getOverdueTasks(@Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>();
		return this.invokeService(request, bindingResult, (t, sr) -> t.getOverdueTasks(sr), serReq, this.service);
	}

	/**
	 * Returns tasks with given status.
	 * 
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/status/{status}")
	public List<TaskEntity> getTasksByStatus(@PathVariable("status") String status, @Parameter(hidden=true) ApiRequest request,
			BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>(status);
		return this.invokeService(request, bindingResult, (t, sr) -> t.getTasksByStatus(sr), serReq, this.service);
	}

	/**
	 * Returns completed tasks in give date range.
	 * 
	 * @param apiReq
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/completed")
	public List<TaskEntity> getCompletedTaskWithDataRange(@Parameter(hidden=true) TaskRequest apiReq, BindingResult bindingResult) {
		apiReq.validateCompletedTaskRequest(bindingResult);
		var serReq = new ServiceRequest<>(apiReq);
		return this.invokeService(apiReq, bindingResult, (t, sr) -> t.getCompletedTaskWithDataRange(sr), serReq,
				this.service);
	}

	/**
	 * Return Task statistics
	 * 
	 * @param request
	 * @param bindingResult
	 * @return
	 */
	public TaskMetrics getTaskMetrics(@Parameter(hidden=true) ApiRequest request, BindingResult bindingResult) {
		request.validate(bindingResult);
		var serReq = new ServiceRequest<>();
		return this.invokeService(request, bindingResult, (t, sr) -> t.getTaskMetrics(sr), serReq, this.service);
	}

}
