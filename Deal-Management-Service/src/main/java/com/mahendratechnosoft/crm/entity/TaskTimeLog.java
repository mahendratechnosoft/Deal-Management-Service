package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import com.mahendratechnosoft.crm.enums.TimeLogStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class TaskTimeLog {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String taskLogId;

    private String taskId;
    private String adminId;
    private String employeeId;
    private String name;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Column(length = 1000)
    private String endNote;

    private Long durationInMinutes; 
    
    @Enumerated(EnumType.STRING)
    private TimeLogStatus status;

	public String getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(String taskLogId) {
		this.taskLogId = taskLogId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(Long durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public String getEndNote() {
		return endNote;
	}

	public void setEndNote(String endNote) {
		this.endNote = endNote;
	}

	public TimeLogStatus getStatus() {
		return status;
	}

	public void setStatus(TimeLogStatus status) {
		this.status = status;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	} 
}