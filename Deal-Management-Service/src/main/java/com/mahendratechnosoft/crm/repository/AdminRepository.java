package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.dto.AdminInfoDto;
import com.mahendratechnosoft.crm.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	@Query("SELECT a FROM Admin a JOIN FETCH a.user WHERE a.loginEmail = :loginEmail")
    Optional<Admin> findByLoginEmail(String loginEmail);
	
	
	@Query("""
		    SELECT c FROM Admin c 
		    WHERE (:search IS NULL OR LOWER(c.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY c.adminId DESC
		""")
		Page<Admin> getAllAdmin(
		        @Param("search") String search,
		        Pageable pageable);
	
	 Page<Admin> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
	 
	 
	  @Query("""
		        SELECT new com.mahendratechnosoft.crm.dto.AdminInfoDto(
		            a.adminId,
		            a.loginEmail,
		            a.companyEmail,
		            a.name,
		            a.phone,
		            a.address,
		            a.companyName,
		            a.gstNumber
		        )
		        FROM Admin a
		        ORDER BY a.adminId DESC
		    """)
		    Page<AdminInfoDto> findAllAdmins(String serach,Pageable pageable);
	  
	  
	  
		@Query("SELECT a FROM Admin a JOIN FETCH a.user WHERE a.adminId = :adminId")
	    Optional<Admin> findByAdmin(String adminId);
	
}
