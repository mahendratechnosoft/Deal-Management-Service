package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	@Query("SELECT e FROM Employee e JOIN FETCH e.admin a JOIN FETCH a.user u WHERE e.loginEmail = :loginEmail")
	Optional<Employee> findByLoginEmail(String loginEmail);
	
	
	@Query(
        // This is the query to get the actual data
        value = "SELECT e FROM Employee e WHERE e.admin = :admin AND " +
                "(" +
                "  :searchTerm IS NULL OR :searchTerm = '' OR " +
                "  LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "  LOWER(e.loginEmail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "  LOWER(e.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
                ")",
        
        // This query gets the total count of matching records, which is required for pagination
        countQuery = "SELECT COUNT(e) FROM Employee e WHERE e.admin = :admin AND " +
                     "(" +
                     "  :searchTerm IS NULL OR :searchTerm = '' OR " +
                     "  LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                     "  LOWER(e.loginEmail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                     "  LOWER(e.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
                     ")"
    )
    Page<Employee> findByAdminWithPagination(
            @Param("admin") Admin admin, 
            @Param("searchTerm") String searchTerm, 
            Pageable pageable
    );
	
	boolean existsByLoginEmail(String loginEmail);
}
