package com.mahendratechnosoft.crm.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.mahendratechnosoft.crm.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Task {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String taskId;
	private String adminId;
	private String employeeId;
	private String subject;
	private Date startDate;
	private Date endDate;
	private String priority;
	private String relatedTo;
	private String relatedToId;
	private String relatedToName;
	private double hourlyRate;
	private double estimatedHours;
	@Column(length = 500)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "assignees",
	    joinColumns = @JoinColumn(name = "task_id"),
	    inverseJoinColumns = @JoinColumn(name = "employee_id")
	)
	@JsonIncludeProperties({"employeeId", "name"})
	private Set<Employee> assignedEmployees = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "followers",
	    joinColumns = @JoinColumn(name = "task_id"),
	    inverseJoinColumns = @JoinColumn(name = "employee_id")
	)
	@JsonIncludeProperties({"employeeId", "name"})
	private Set<Employee> followersEmployees = new HashSet<>();
	
	private LocalDateTime createdAt;
	private String createdBy;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getRelatedTo() {
		return relatedTo;
	}

	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}

	public String getRelatedToId() {
		return relatedToId;
	}

	public void setRelatedToId(String relatedToId) {
		this.relatedToId = relatedToId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getRelatedToName() {
		return relatedToName;
	}

	public void setRelatedToName(String relatedToName) {
		this.relatedToName = relatedToName;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public double getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Set<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void setAssignedEmployees(Set<Employee> assignedEmployees) {
		this.assignedEmployees = assignedEmployees;
	}

	public Set<Employee> getFollowersEmployees() {
		return followersEmployees;
	}

	public void setFollowersEmployees(Set<Employee> followersEmployees) {
		this.followersEmployees = followersEmployees;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
