/**
 * 
 */
package com.test.api.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.api.task.constants.StatusEnum;
import com.test.api.task.entity.TaskEntity;
import com.test.api.task.exception.DNFException;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.model.ServiceResponse;
import com.test.api.task.model.TaskMetrics;
import com.test.api.task.model.TaskRequest;
import com.test.api.task.repo.TaskRepository;
import com.test.api.task.repo.UserRepository;
import com.test.api.task.util.ServiceResponseHelper;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TaskServiceImpl.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 12:25:44 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskServiceI {

	@Autowired
	private ServiceResponseHelper helper;

	@Autowired
	private TaskRepository repo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EntityManager entityManager;
	
	/**
	 * 
	 * @see com.test.api.task.service.TaskServiceI#findAll(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<List<TaskEntity>> findAll(ServiceRequest<?> req) {
		log.debug("Started >>");
		List<TaskEntity> list = new ArrayList<>();
		this.repo.findAll().forEach(list::add);
		log.debug("Ended >>");
		return this.helper.buildResponse(list);
	}

	/**
	 * 
	 * @see com.test.api.task.service.TaskServiceI#deleteById(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<TaskEntity> deleteById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		this.repo.deleteById(req.getPayload());
		var rec = new TaskEntity();
		rec.setTaskId(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}
	
	/**
	 * 
	 * @see com.test.api.task.service.TaskServiceI#findById(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<TaskEntity> findById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		var rec = this.repo.findById(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#create(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<TaskEntity> create(ServiceRequest<TaskEntity> req) {
		log.debug("Started >>");
		var rec = this.repo.save(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#update(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<TaskEntity> update(ServiceRequest<TaskEntity> req) {
		log.debug("Started >>");
		
		var payload = req.getPayload();
		payload.setUpdatedDate(new Date());
		
		if(StatusEnum.COMPLETED == payload.getStatus() && payload.getCompletedDate() == null) {
			payload.setCompletedDate(new Date());
		}
		
		var rec = this.repo.save(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#assignTask(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<TaskEntity> assignTask(ServiceRequest<TaskRequest> req) {
		log.debug("Started >>");
		var payload = req.getPayload();
		var usr = this.userRepo.findById(payload.getUserId());
		if(usr.isEmpty()) {
			throw new DNFException("User not found with userId: " + payload.getUserId());
		}
		var taskOpt = this.repo.findById(payload.getTaskId());
		if(taskOpt.isEmpty()) {
			throw new DNFException("Task not found with userId: " + payload.getTaskId());
		}
		var task = taskOpt.get();
		task.setUserId(payload.getUserId());
		task.setUpdatedDate(new Date());
		// task.setStatus(StatusEnum.OPEN);
		var rec = this.repo.save(task);
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#updateTaskProgress(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<TaskEntity> updateTaskProgress(ServiceRequest<TaskRequest> req) {
		log.debug("Started >>");
		var payload = req.getPayload();
		var taskOpt = this.repo.findById(payload.getTaskId());
		if(taskOpt.isEmpty()) {
			throw new DNFException("Task not found with taskId: " + payload.getTaskId());
		}
		var task = taskOpt.get();
		task.setUpdatedDate(new Date());
		if(null != payload.getStatus()) {
			task.setStatus(payload.getStatus());
		}
		if(null != payload.getProgress()) {
			task.setProgress(payload.getProgress());
		}
		var rec = this.repo.save(task);
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#getOverdueTasks(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<List<TaskEntity>> getOverdueTasks(ServiceRequest<?> req) {
		log.debug("Started >>");
		var list = this.repo.findOverdueTasks(new Date());
		log.debug("Started >>");
		return this.helper.buildResponse(list);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#getTasksByStatus(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<List<TaskEntity>> getTasksByStatus(ServiceRequest<String> req) {
		log.debug("Started >>");
		var list = this.repo.findByStatus(req.getPayload());
		log.debug("Started >>");
		return this.helper.buildResponse(list);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#getTaskMetrics(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<TaskMetrics> getTaskMetrics(ServiceRequest<?> req) {
		var tm = new TaskMetrics();
		List<Object[]> rs = this.entityManager.createQuery("Select t.status, count(t.status) from TaskEntity t group by t.status").getResultList();
		
		Map<String, Double> map = new HashMap<>();
		rs.forEach(r -> {
			 String status = (String) r[0];
			 Double count = (Double) r[1];
			 map.put(status, count);
		});
		var total = map.values().stream().mapToDouble(v -> v).sum();
		tm.setTotalTasks(total);
		var openCount = map.get("OPEN") != null ? map.get("OPEN") : 0;
		tm.setTotalOpenTasks(openCount);
		tm.setOpenTasksPercentage(this.getPrecentage(openCount, total));
		var compCount = map.get("COMPLETED") != null ? map.get("COMPLETED") : 0;
		tm.setTotalCompletedTasks(compCount);
		tm.setCompletedTasksPercentage(this.getPrecentage(compCount, total));
		var inProgCount = map.get("INPROGRESS") != null ? map.get("INPROGRESS") : 0;
		tm.setTotalInprogresTasks(inProgCount);
		tm.setInpgrogressTasksPercentage(this.getPrecentage(inProgCount, total));
		return this.helper.buildResponse(tm);
	}

	/**
	 * @see com.test.api.task.service.TaskServiceI#getCompletedTaskWithDataRange(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<List<TaskEntity>> getCompletedTaskWithDataRange(ServiceRequest<TaskRequest> req) {
		log.debug("Started >>");
		var payload = req.getPayload();
		var list = this.repo.findCompletedTaskWithDataRange(payload.getStartDate(), payload.getEndDate());
		log.debug("Started >>");
		return this.helper.buildResponse(list);
	}

	/**
	 * 
	 * @param count
	 * @param total
	 * @return
	 */
	private double getPrecentage(double count, double total) {
		return count * 100 / total;
	}
	
	
}
