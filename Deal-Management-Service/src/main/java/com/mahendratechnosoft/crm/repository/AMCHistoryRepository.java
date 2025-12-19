package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mahendratechnosoft.crm.entity.AMCHistory;

import jakarta.transaction.Transactional;

public interface AMCHistoryRepository extends JpaRepository<AMCHistory, String>{

	@Modifying
    @Transactional
    @Query("DELETE FROM AMCHistory a WHERE a.amcId = :amcId")
    void deleteByAmcId(String amcId);
	
	List<AMCHistory> findByAmcIdOrderBySequenceDesc(String amcId);
	
	boolean existsByAmcIdAndSequence(String amcId, int sequence);

	List<AMCHistory> findByAmcIdAndIsDeletedFalseOrderBySequenceDesc(String amcId);

}
