/**
 * 
 */
package com.test.api.task.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * TaskMetrics.java.java
 * 
 * @author Naveen
 * @Version 1.0 <BR>
 *      <BR>
 *      <B> Revision History: </B>
 *      <UL>
 *      <LI> Sep 30, 2023 10:54:48 AM (Naveen) Baseline</LI>
 *      </UL>
 *
 */
@Setter
@Getter
public class TaskMetrics {

	private double totalTasks;
	
	private double totalOpenTasks;
	private double openTasksPercentage;
	
	private double totalInprogresTasks;
	private double inpgrogressTasksPercentage;
	
	private double totalCompletedTasks;
	private double completedTasksPercentage;
}
