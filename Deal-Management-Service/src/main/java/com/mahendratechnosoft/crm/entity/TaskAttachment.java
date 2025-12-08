package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;

@Entity
public class TaskAttachment {
	
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String taskAttachmentId;
    private String taskId;
    private String fileName;
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    private LocalDateTime uploadedAt;
    private String uploadedBy;
    
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
    
	public String getTaskAttachmentId() {
		return taskAttachmentId;
	}
	public void setTaskAttachmentId(String taskAttachmentId) {
		this.taskAttachmentId = taskAttachmentId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
    
    

}
