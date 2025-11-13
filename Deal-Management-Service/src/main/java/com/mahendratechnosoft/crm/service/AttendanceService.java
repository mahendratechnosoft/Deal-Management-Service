package com.mahendratechnosoft.crm.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mahendratechnosoft.crm.dto.AttendanceEmployeeDTO;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Attendance;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Payments;
import com.mahendratechnosoft.crm.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	public ResponseEntity<?> addAttendance( String adminId,String employeeId,boolean status) {

		try {
          
			Attendance attendance=new Attendance();
			attendance.setAdminId(adminId);
			attendance.setEmployeeId(employeeId);
			attendance.setStatus(status);
			attendance.setTimeStamp(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli());
          
			attendanceRepository.save(attendance);
			
			return ResponseEntity.ok(attendance);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> getLatestTime( String employeeId) {

		try {
			
	        Long timeStamp = attendanceRepository.findLatestTimestamp(employeeId);

	        if (timeStamp == null) {
	            return ResponseEntity.ok("No attendance record found for this employee.");
	        }
	        
	        return ResponseEntity.ok(timeStamp);
			
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	

	public ResponseEntity<?> getAttendanceBetweenForParticalurEmployee(String employeeId, String fromDate,String toDate) {

	    try {
	    	 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         ZoneId zone = ZoneId.of("Asia/Kolkata");

	         long fromTime = LocalDate.parse(fromDate, dateFormatter)
	                 .atStartOfDay(zone)
	                 .toInstant()
	                 .toEpochMilli();

	         long toTime = LocalDate.parse(toDate, dateFormatter)
	                 .plusDays(1)
	                 .atStartOfDay(zone)
	                 .toInstant()
	                 .toEpochMilli() - 1;

	         List<Attendance> records = attendanceRepository.findAttendanceBetween(employeeId, fromTime, toTime);

	         if (records.isEmpty()) {
	             return ResponseEntity.ok("No attendance records found in this date range.");
	         }

	         // ✅ Force compiler to use String as the map key type
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	         Map<String, List<Attendance>> groupedByDate = records.stream()
	                 .collect(Collectors.groupingBy(
	                         (Attendance a) -> {
	                             String dateKey = Instant.ofEpochMilli(a.getTimeStamp())
	                                     .atZone(zone)
	                                     .toLocalDate()
	                                     .format(formatter);
	                             return dateKey;
	                         },
	                         LinkedHashMap::new,
	                         Collectors.toList()
	                 ));

	         return ResponseEntity.ok(groupedByDate);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}

	
	
	public ResponseEntity<?> getAttendanceBetween(Object loginUser, String fromDate,String toDate) {

	    try {
	    	
	    	
	    	String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
		    List<AttendanceEmployeeDTO> records = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
			}
	    	
	    	 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         ZoneId zone = ZoneId.of("Asia/Kolkata");

	         long fromTime = LocalDate.parse(fromDate, dateFormatter)
	                 .atStartOfDay(zone)
	                 .toInstant()
	                 .toEpochMilli();

	         long toTime = LocalDate.parse(toDate, dateFormatter)
	                 .plusDays(1)
	                 .atStartOfDay(zone)
	                 .toInstant()
	                 .toEpochMilli() - 1;
	         
	         if (role.equals("ROLE_ADMIN")) {
	        	 
	        	 records = attendanceRepository.findAttendanceBetweenByAdmin(adminId, fromTime, toTime);
	        	 
	         }else {
	        	 
	        	   records = attendanceRepository.findAttendanceBetweenByEmployee(employeeId, fromTime, toTime);
	         }

	       

	         if (records.isEmpty()) {
	             return ResponseEntity.ok("No attendance records found in this date range.");
	         }

	       
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	      // ✅ Step 1: Group by employeeId
	         Map<String, List<AttendanceEmployeeDTO>> groupedByEmployeeId = records.stream()
	                 .collect(Collectors.groupingBy(
	                         AttendanceEmployeeDTO::getEmployeeId,
	                         LinkedHashMap::new,
	                         Collectors.toList()
	                 ));

	         // ✅ Step 2: Convert key from employeeId → employeeName and group inside by date
	         Map<String, Map<String, List<AttendanceEmployeeDTO>>> groupedByNameAndDate = new LinkedHashMap<>();

	         for (Map.Entry<String, List<AttendanceEmployeeDTO>> entry : groupedByEmployeeId.entrySet()) {
	             List<AttendanceEmployeeDTO> employeeRecords = entry.getValue();

	            
	             String employeeName = employeeRecords.get(0).getEmployeeName();

	             
	             Map<String, List<AttendanceEmployeeDTO>> byDate = employeeRecords.stream()
	            		 .filter(r -> r.getTimeStamp() != null) 
	                     .collect(Collectors.groupingBy(
	                             r -> Instant.ofEpochMilli(r.getTimeStamp())
	                                     .atZone(zone)
	                                     .toLocalDate()
	                                     .format(formatter),
	                             LinkedHashMap::new,
	                             Collectors.toList()
	                     ));

	             groupedByNameAndDate.put(employeeName, byDate);
	         }

	         return ResponseEntity.ok(groupedByNameAndDate);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}

	

	public ResponseEntity<?> updateAttendance(@RequestBody Attendance attendance) {

		try {
          
			
			attendanceRepository.save(attendance);
			
			return ResponseEntity.ok(attendance);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> deleteAttendance(String  attendanceId) {

		try {
          
			
			attendanceRepository.deleteById(attendanceId);
			
			return ResponseEntity.ok("Attendance Deleted Successfuly");

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

}
