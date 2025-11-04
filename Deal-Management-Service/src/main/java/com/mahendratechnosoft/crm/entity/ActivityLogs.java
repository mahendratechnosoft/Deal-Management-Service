package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class ActivityLogs {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String activityId;
	private String adminId;
	private String moduleType;
	private String moduleId;
	private String createdBy;
	private LocalDateTime createdDateTime;
	private String description;
	
	public ActivityLogs(String activityId, String adminId, String moduleType, String moduleId, String createdBy,
			LocalDateTime createdDateTime, String description) {
		super();
		this.activityId = activityId;
		this.adminId = adminId;
		this.moduleType = moduleType;
		this.moduleId = moduleId;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.description = description;
	}
	public ActivityLogs() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
