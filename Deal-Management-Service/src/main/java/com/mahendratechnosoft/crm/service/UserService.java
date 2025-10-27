package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new Admin.
     * This creates one User (for login) and one Admin (for profile)
     * and links them together.
     */
    @Transactional // Ensures both save operations succeed or fail together
    public void registerAdmin(AdminRegistrationDto dto) {
        
        // 1. Create the User entity
        User user = new User();
        user.setLoginEmail(dto.getUsername());
        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Encode password
        
        // Set expiry date
        LocalDate expiryDay = LocalDate.parse("2027-11-08");
        LocalDateTime expiryDateTime = expiryDay.atTime(LocalTime.MAX);
        user.setExpiryDate(expiryDateTime);
        
        // 2. Create the Admin entity
        Admin admin = new Admin();
        admin.setLoginEmail(dto.getUsername());

        // 3. Link the entities (This is the crucial part)
        admin.setUser(user); // Tell the Admin which User it belongs to
        user.setAdmin(admin); // Tell the User which Admin profile it has

        // 4. Save the User.
        // Because of 'CascadeType.ALL' on the User.admin field,
        // saving the User will AUTOMATICALLY save the Admin.
        userRepository.save(user);
    }
}