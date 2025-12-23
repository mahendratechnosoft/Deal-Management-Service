package com.mahendratechnosoft.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	@Query("""
		    SELECT c FROM Customer c 
		    WHERE c.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(c.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY c.customerId DESC
		""")
		Page<Customer> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT c FROM Customer c 
		    WHERE c.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(c.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY c.customerId DESC
		""")
		Page<Customer> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        Pageable pageable);

	@Query("SELECT c.id, c.companyName FROM Customer c WHERE c.adminId = :adminId")
    List<Object[]> findCompanyNameAndIdsByCompanyId(@Param("adminId") String adminId);
    
    Customer findByCustomerId(String customerId);
    
    
    @Query("SELECT c.status, COUNT(c) FROM Customer c WHERE c.adminId = :adminId GROUP BY c.status")
    List<Object[]> countLeadsByStatusAndAdminId(@Param("adminId") String adminId);


    boolean existsByCompanyNameAndAdminId(String companyName,String adminId);


	Optional<Customer> findByUserId(String userId);
}
