package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Attendance {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String attendanceId;
	private String adminId;
	private String employeeId;
    private long timeStamp;
    private boolean status;
    
    
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Attendance(String attendanceId, String adminId, String employeeId, long timeStamp, boolean status) {
		super();
		this.attendanceId = attendanceId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.timeStamp = timeStamp;
		this.status = status;
	}
	public String getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
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
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
    
    
}
