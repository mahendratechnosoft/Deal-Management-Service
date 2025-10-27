package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(String username, String rawPassword) {
        User user = new User();
        user.setLoginEmail(username);
        user.setRole("ROLE_ADMIN");
        
        LocalDate expiryDay = LocalDate.parse("2027-11-08");
        LocalDateTime expiryDateTime = expiryDay.atTime(LocalTime.MAX);
        user.setExpiryDate(expiryDateTime);
        
        user.setPassword(passwordEncoder.encode(rawPassword));
        User save = userRepository.save(user);
        
        if(save != null) {
        	Admin  admin = new Admin();
        	admin.setLoginEmail(save.getLoginEmail());
        	admin.setUserId(save.getUserId());
        	adminRepository.save(admin);
        }
    }
}