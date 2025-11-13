package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.dto.AttendanceEmployeeDTO;
import com.mahendratechnosoft.crm.entity.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

	@Query("SELECT MAX(a.timeStamp) FROM Attendance a WHERE a.employeeId = :employeeId")
	Long findLatestTimestamp(@Param("employeeId") String employeeId);

	
	@Query("""
		    SELECT a 
		    FROM Attendance a 
		    WHERE a.employeeId = :employeeId 
		      AND a.timeStamp BETWEEN :fromTime AND :toTime
		    ORDER BY a.timeStamp ASC
		""")
		List<Attendance> findAttendanceBetween(
		        @Param("employeeId") String employeeId,
		        @Param("fromTime") long fromTime,
		        @Param("toTime") long toTime);
	
	
	
	
	
	@Query("""
		    SELECT new com.mahendratechnosoft.crm.dto.AttendanceEmployeeDTO(
		     a.attendanceId,
		      a.adminId,
		        e.employeeId,
		        e.name,
		        a.timeStamp,
		        a.status
		    )
		    FROM Employee e
		    LEFT JOIN Attendance a
		        ON e.employeeId = a.employeeId
		        AND a.adminId = :adminId
		        AND a.timeStamp BETWEEN :fromTime AND :toTime
		    WHERE e.admin.adminId = :adminId
		    ORDER BY e.name ASC, a.timeStamp ASC
		""")
		List<AttendanceEmployeeDTO> findAttendanceBetweenByAdmin(
		        @Param("adminId") String adminId,
		        @Param("fromTime") long fromTime,
		        @Param("toTime") long toTime);

	
	
	@Query("""
		    SELECT new com.mahendratechnosoft.crm.dto.AttendanceEmployeeDTO(
		        a.attendanceId,
		        a.adminId,
		        a.employeeId,
		        e.name,
		        a.timeStamp,
		        a.status
		    )
		    FROM Attendance a
		    JOIN Employee e ON a.employeeId = e.employeeId
		    WHERE a.employeeId = :employeeId
		      AND a.timeStamp BETWEEN :fromTime AND :toTime
		    ORDER BY a.timeStamp ASC
		""")
		List<AttendanceEmployeeDTO> findAttendanceBetweenByEmployee(
		        @Param("employeeId") String employeeId,
		        @Param("fromTime") long fromTime,
		        @Param("toTime") long toTime);


}
