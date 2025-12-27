package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class EmailConfiguration {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String id;
	private String adminId;
	private String gmail;  // gamil Email
	private String gmailPassword;
	private String hostingerMail;
	private String hostingerPassword;
	private String activeHost;
	
	
	public EmailConfiguration(String id, String adminId,  String gmail, String gmailPassword,
			String hostingerMail, String hostingerPassword, String activeHost) {
		super();
		this.id = id;
		this.adminId = adminId;
		this.gmail = gmail;
		this.gmailPassword = gmailPassword;
		this.hostingerMail = hostingerMail;
		this.hostingerPassword = hostingerPassword;
		this.activeHost = activeHost;
	}
	public EmailConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getGmailPassword() {
		return gmailPassword;
	}
	public void setGmailPassword(String gmailPassword) {
		this.gmailPassword = gmailPassword;
	}
	public String getHostingerMail() {
		return hostingerMail;
	}
	public void setHostingerMail(String hostingerMail) {
		this.hostingerMail = hostingerMail;
	}
	public String getHostingerPassword() {
		return hostingerPassword;
	}
	public void setHostingerPassword(String hostingerPassword) {
		this.hostingerPassword = hostingerPassword;
	}
	public String getActiveHost() {
		return activeHost;
	}
	public void setActiveHost(String activeHost) {
		this.activeHost = activeHost;
	}
	
	
}
