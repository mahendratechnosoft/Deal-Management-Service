package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Contacts;

public interface ContactsRepository  extends JpaRepository<Contacts, String>{

	
	List<Contacts> findByCustomerIdAndNameContainingIgnoreCaseOrderByIdDesc(String customerId, String name);

}
