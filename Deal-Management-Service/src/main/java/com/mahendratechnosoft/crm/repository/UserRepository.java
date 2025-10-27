package com.mahendratechnosoft.crm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mahendratechnosoft.crm.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	User findByLoginEmail(String loginEmail);
}
