package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.InvoiceContent;

public interface InvoiceContentRepository extends JpaRepository<InvoiceContent, String>{

	List<InvoiceContent> findByInvoiceId(String invoiceId);
	
}
