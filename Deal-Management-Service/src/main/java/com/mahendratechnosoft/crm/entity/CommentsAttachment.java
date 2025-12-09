package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class CommentsAttachment {
	
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String commentAttachmentId;
    private String fileName;
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    private LocalDateTime uploadedAt;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private TaskComments taskComment;
    
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
    
	
	public String getCommentAttachmentId() {
		return commentAttachmentId;
	}


	public void setCommentAttachmentId(String commentAttachmentId) {
		this.commentAttachmentId = commentAttachmentId;
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

	public TaskComments getTaskComment() {
		return taskComment;
	}


	public void setTaskComment(TaskComments taskComment) {
		this.taskComment = taskComment;
	}
    
}
