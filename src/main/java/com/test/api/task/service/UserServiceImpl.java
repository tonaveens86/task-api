/**
 * 
 */
package com.test.api.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.api.task.entity.TaskEntity;
import com.test.api.task.entity.UserEntity;
import com.test.api.task.exception.DNFException;
import com.test.api.task.model.ServiceRequest;
import com.test.api.task.model.ServiceResponse;
import com.test.api.task.repo.UserRepository;
import com.test.api.task.util.ServiceResponseHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * UserServiceImpl.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 12:09:19 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {
	
	@Autowired
	private ServiceResponseHelper helper;
	
	@Autowired
	private UserRepository repo;

	/**
	 * 
	 * @see com.test.api.task.service.UserServiceI#findAll(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<List<UserEntity>> findAll(ServiceRequest<?> req) {
		log.debug("Started >>");
		List<UserEntity> list = new ArrayList<>();
		this.repo.findAll().forEach(list::add);
		log.debug("Ended >>");
		return this.helper.buildResponse(list);
	}

	/**
	 * 
	 * @see com.test.api.task.service.UserServiceI#deleteById(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<UserEntity> deleteById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		this.repo.deleteById(req.getPayload());
		var rec = new UserEntity();
		rec.setUserId(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}
	
	/**
	 * 
	 * @see com.test.api.task.service.UserServiceI#findById(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(readOnly = true)
	@Override
	public ServiceResponse<UserEntity> findById(ServiceRequest<Long> req) {
		log.debug("Started >>");
		var rec = this.repo.findById(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.UserServiceI#create(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<UserEntity> create(ServiceRequest<UserEntity> req) {
		log.debug("Started >>");
		var rec = this.repo.save(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.UserServiceI#update(com.test.api.task.model.ServiceRequest)
	 */
	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ServiceResponse<UserEntity> update(ServiceRequest<UserEntity> req) {
		log.debug("Started >>");
		var rec = this.repo.save(req.getPayload());
		log.debug("Ended >>");
		return this.helper.buildResponse(rec);
	}

	/**
	 * @see com.test.api.task.service.UserServiceI#getAssignedTasks(com.test.api.task.model.ServiceRequest)
	 */
	@Override
	public ServiceResponse<List<TaskEntity>> getAssignedTasks(ServiceRequest<Long> req) {
		log.debug("Started >>");
		var rec = this.repo.findById(req.getPayload());
		if(rec.isPresent()) {
			var list = rec.get().getTasks();
			log.debug("Ended >>");
			return this.helper.buildResponse(list);
		}
		log.debug("Ended >>");
		throw new DNFException();
	}

}
