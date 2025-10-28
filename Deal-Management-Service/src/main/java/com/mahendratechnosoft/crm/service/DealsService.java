package com.mahendratechnosoft.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.repository.DealsRepository;

@Service
public class DealsService {
	
	@Autowired
	DealsRepository dealsRepository;
	
	

}
