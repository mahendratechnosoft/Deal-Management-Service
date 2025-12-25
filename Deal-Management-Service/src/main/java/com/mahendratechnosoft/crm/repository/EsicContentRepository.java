package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.EsicContent;

@Repository
public interface EsicContentRepository extends JpaRepository<EsicContent, String>{

	List<EsicContent> findByEsicId(String esicId);
}
