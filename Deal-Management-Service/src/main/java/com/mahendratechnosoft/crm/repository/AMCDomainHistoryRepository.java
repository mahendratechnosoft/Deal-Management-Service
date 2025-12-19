package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mahendratechnosoft.crm.entity.AMCDomainHistory;

import jakarta.transaction.Transactional;

public interface AMCDomainHistoryRepository extends JpaRepository<AMCDomainHistory, String>{
	
	
	@Modifying
    @Transactional
    @Query("DELETE FROM AMCDomainHistory a WHERE a.amcId = :amcId")
    void deleteByAmcId(String amcId);
	
	List<AMCDomainHistory> findByAmcId(String amcId);
	
	boolean existsByAmcIdAndSequence(String amcId, int sequence);

	List<AMCDomainHistory> findByAmcIdAndIsDeletedFalse(String amcId);

}
