package com.mahendratechnosoft.crm.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mahendratechnosoft.crm.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByLoginEmail(String loginEmail);

	boolean existsByLoginEmail(String loginEmail);
}
