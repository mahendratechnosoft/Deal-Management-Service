package com.mahendratechnosoft.crm.repository.Hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Hospital.FamilyInfo;

public interface FamilyInfoRepository extends JpaRepository<FamilyInfo, String> {

	@Query("""
			    SELECT p FROM FamilyInfo p
			    WHERE p.employeeId = :employeeId
			      AND (:search IS NULL OR LOWER(p.wifeName) LIKE LOWER(CONCAT('%', :search, '%')))
			     AND (:search IS NULL OR LOWER(p.husbandName) LIKE LOWER(CONCAT('%', :search, '%')))
			     AND (:search IS NULL OR LOWER(p.uin) LIKE LOWER(CONCAT('%', :search, '%')))
			    ORDER BY p.familyInfoId DESC
			""")
	Page<FamilyInfo> findByEmployeeId(@Param("employeeId") String employeeId, @Param("search") String search,
		 Pageable pageable);

	@Query("""
			    SELECT p FROM FamilyInfo p
			    WHERE p.adminId = :adminId
			      AND ((:search IS NULL OR LOWER(p.wifeName) LIKE LOWER(CONCAT('%', :search, '%')))
			     OR (:search IS NULL OR LOWER(p.husbandName) LIKE LOWER(CONCAT('%', :search, '%')))
			     OR (:search IS NULL OR LOWER(p.uin) LIKE LOWER(CONCAT('%', :search, '%')))
			     )
			    ORDER BY p.familyInfoId DESC
			""")
	Page<FamilyInfo> findByAdminId(@Param("adminId") String adminId, @Param("search") String search,
			 Pageable pageable);

}
