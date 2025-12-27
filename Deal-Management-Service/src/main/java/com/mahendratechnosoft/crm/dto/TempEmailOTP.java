package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TempEmailOTP {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    private String email;
    private String otp;
    private LocalDateTime expiresAt;
	public TempEmailOTP() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TempEmailOTP(String id, String email, String otp, LocalDateTime expiresAt) {
		super();
		this.id = id;
		this.email = email;
		this.otp = otp;
		this.expiresAt = expiresAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
    
    

}
