package com.mahendratechnosoft.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mahendratechnosoft.crm.config.UserDetailServiceImp;
import com.mahendratechnosoft.crm.dto.AdminRegistrationDto;
import com.mahendratechnosoft.crm.dto.SignInRespoonceDto;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.helper.SoftwareValidityExpiredException;
import com.mahendratechnosoft.crm.repository.UserRepository;
import com.mahendratechnosoft.crm.security.JwtUtil;
import com.mahendratechnosoft.crm.service.UserService;


@Controller
@RestController
public class HomeController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
	private UserDetailServiceImp userDetailServiceImp;
    
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AdminRegistrationDto registrationDto) {
        try {
            userService.registerAdmin(registrationDto);
            return ResponseEntity.ok("Admin registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> generateToken(@RequestBody AdminRegistrationDto adminRegistrationDto) {
        try {
        	
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminRegistrationDto.getUsername(), adminRegistrationDto.getPassword())
            );
            final UserDetails userDetails = userDetailServiceImp.loadUserByUsername(adminRegistrationDto.getUsername());
            User user = userRepository.findByLoginEmail(adminRegistrationDto.getUsername()).get();
            
            final String token = jwtUtil.generateToken(userDetails);
            
            SignInRespoonceDto signInRespoonceDto = new SignInRespoonceDto(token, user.getUserId(), user.getLoginEmail(), user.getRole(), user.getExpiryDate());
            
            return ResponseEntity.ok(signInRespoonceDto);

        } catch (SoftwareValidityExpiredException s) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(s.getMessage());
        } catch (UsernameNotFoundException u) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + u.getMessage());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + ex.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    


}
