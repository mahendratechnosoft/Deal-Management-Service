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
public class EsicContent {

	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String esicContentId;
	private String esicId;
	
	private String name;
	private String relation;
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] aadhaarPhoto;
    
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] passportPhoto;
    
    private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}

	public String getEsicContentId() {
		return esicContentId;
	}

	public void setEsicContentId(String esicContentId) {
		this.esicContentId = esicContentId;
	}
	
	public String getEsicId() {
		return esicId;
	}

	public void setEsicId(String esicId) {
		this.esicId = esicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public byte[] getAadhaarPhoto() {
		return aadhaarPhoto;
	}

	public void setAadhaarPhoto(byte[] aadhaarPhoto) {
		this.aadhaarPhoto = aadhaarPhoto;
	}

	public byte[] getPassportPhoto() {
		return passportPhoto;
	}

	public void setPassportPhoto(byte[] passportPhoto) {
		this.passportPhoto = passportPhoto;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
