package com.mahendratechnosoft.crm.config;


import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.helper.SoftwareValidityExpiredException;
import com.mahendratechnosoft.crm.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    
	    // 1. Get the current time ONCE.
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    System.err.println(username);
	    User user = userRepository.findByLoginEmail(username).get();
	    
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found with email: " + username);
	    }

	    if (user.getExpiryDate() != null && currentDateTime.isAfter(user.getExpiryDate())) {
	        
	        if ("ROLE_ADMIN".equals(user.getRole())) {
	            throw new SoftwareValidityExpiredException("Your Subscription is Expired, Please Get Subscription");
	        } else if ("ROLE_EMP".equals(user.getRole())) {
	            throw new SoftwareValidityExpiredException("Your Company Subscription is Expired");
	        } else {
	            throw new SoftwareValidityExpiredException("Your account subscription has expired.");
	        }
	    }

	    return new CustomUserDetail(user);
	}
}
