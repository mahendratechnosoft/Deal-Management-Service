package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.AdminRegistrationDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerAdmin(AdminRegistrationDto dto) {
        User user = new User();
        user.setLoginEmail(dto.getUsername());
        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        LocalDate expiryDay = LocalDate.parse("2027-11-08");
        LocalDateTime expiryDateTime = expiryDay.atTime(LocalTime.MAX);
        user.setExpiryDate(expiryDateTime);
        
        User savedUser = userRepository.save(user);
        

        Admin admin = new Admin();
        admin.setLoginEmail(savedUser.getLoginEmail());
        admin.setUser(savedUser);
        adminRepository.save(admin);
    }
}