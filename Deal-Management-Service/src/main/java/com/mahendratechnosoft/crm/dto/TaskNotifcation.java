package com.mahendratechnosoft.crm.dto;

import java.sql.Date;
import java.util.Set;

import com.mahendratechnosoft.crm.enums.TaskStatus;

public class TaskNotifcation {
	
	private String taskId;
	private String subject;
	private String adminId;
	private String employeeId;
	private Date startDate;
	private Date endDate;
	private String priority;
	private Set<String> assignTo;
	private String createdBy;
	private TaskStatus status;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Set<String> getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(Set<String> assignTo) {
		this.assignTo = assignTo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	


}
