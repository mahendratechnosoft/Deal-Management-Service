package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class TaskComments {
	
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String commentId;
	private String taskId;
	private String adminId;
	private String employeeId;
	private String commentedBy;
	private LocalDateTime commentedAt;
	
	@Column(nullable = false)
	private boolean isDeleted =false;
	

    @Column(columnDefinition = "TEXT")
    private String content;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "taskComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentsAttachment> attachments;
    
    @PrePersist
    protected void onCreate() {
        this.commentedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}

	public LocalDateTime getCommentedAt() {
		return commentedAt;
	}

	public void setCommentedAt(LocalDateTime commentedAt) {
		this.commentedAt = commentedAt;
	}

	public List<CommentsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<CommentsAttachment> attachments) {
		this.attachments = attachments;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
    
}
