package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.ProformaInvoiceContent;

public interface ProformaInvoiceContentRepository extends JpaRepository<ProformaInvoiceContent, String>{

	List<ProformaInvoiceContent> findByProformaInvoiceId(String proformaInvoiceId);
}
