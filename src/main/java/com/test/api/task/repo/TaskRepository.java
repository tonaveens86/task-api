/**
 * 
 */
package com.test.api.task.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.api.task.entity.TaskEntity;

/**
 * 
 * ProductRepository.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 29, 2023 10:51:43 PM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

	@Query("from TaskEntity t where t.status = ?1")
	List<TaskEntity> findByStatus(String status);
	
	@Query("from TaskEntity t where t.status = 'COMPLETED' and t.completedDate >= ?1 and t.completedDate <= ?2")
	List<TaskEntity> findCompletedTaskWithDataRange(Date startDate, Date endDate);
	
	@Query("from TaskEntity t where t.dueDate > ?1")
	List<TaskEntity> findOverdueTasks(Date date);
}
