package com.mahendratechnosoft.crm.repository.Hospital;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyVialAllocation;

@Repository
public interface FamilyVialAllocationRepository extends JpaRepository<FamilyVialAllocation, String>{
	List<FamilyVialAllocation> findByFamilyInfoIdOrderByAllocationDateDesc(String familyInfoId);
	
	List<FamilyVialAllocation> findByDonorIdOrderByAllocationDateDesc(String donorId);
}
