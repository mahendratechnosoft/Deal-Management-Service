package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.AMCGsuitHistory;

@Repository
public interface AMCGsuitHistoryRepository extends JpaRepository<AMCGsuitHistory, String>{

	List<AMCGsuitHistory> findByAmcIdOrderBySequenceDesc(String amcId);
	
	boolean existsByAmcIdAndSequence(String amcId, int sequence);

	List<AMCGsuitHistory> findByAmcIdAndIsDeletedFalseOrderBySequenceDesc(String amcId);

}
