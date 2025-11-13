package com.mahendratechnosoft.crm.dto;

public class AttendanceEmployeeDTO {

	 private String attendanceId;
	    private String adminId;
	    private String employeeId;
	    private String employeeName;
	    private Long timeStamp;   // changed
	    private Boolean status;   // changed

	    public AttendanceEmployeeDTO(String attendanceId, String adminId,
	                                 String employeeId, String employeeName,
	                                 Long timeStamp, Boolean status) {
	        this.attendanceId = attendanceId;
	        this.adminId = adminId;
	        this.employeeId = employeeId;
	        this.employeeName = employeeName;
	        this.timeStamp = timeStamp;
	        this.status = status;
	    }

	    // ✅ Getters
	    public String getAttendanceId() { return attendanceId; }
	    public String getAdminId() { return adminId; }
	    public String getEmployeeId() { return employeeId; }
	    public String getEmployeeName() { return employeeName; }
	    public Long getTimeStamp() { return timeStamp; }
	    public Boolean getStatus() { return status; }

}
