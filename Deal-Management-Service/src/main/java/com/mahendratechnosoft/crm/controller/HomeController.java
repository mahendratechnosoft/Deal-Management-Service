package com.mahendratechnosoft.crm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mahendratechnosoft.crm.config.UserDetailServiceImp;
import com.mahendratechnosoft.crm.dto.AdminRegistrationDto;
import com.mahendratechnosoft.crm.dto.SignInRespoonceDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.helper.SoftwareValidityExpiredException;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;
import com.mahendratechnosoft.crm.security.JwtUtil;
import com.mahendratechnosoft.crm.service.LeadService;
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
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private LeadService leadService;
    

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
            String name;
            if (user.getRole().equals("ROLE_ADMIN")) {
            	 Optional<Admin> admin=adminRepository.findByLoginEmail(user.getLoginEmail());
            	 name=admin.get().getName();
            }else {
            	Optional<Employee> employee=employeeRepository.findByLoginEmail(user.getLoginEmail());
            	name=employee.get().getName();
            }
            
            SignInRespoonceDto signInRespoonceDto = new SignInRespoonceDto(token, user.getUserId(), user.getLoginEmail(), user.getRole(), user.getExpiryDate(),name);
            
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
    
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmailExists(
            @PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    
    
    @PostMapping("/generateLeads")
    public ResponseEntity<?> genrateLeads(@RequestBody Leads dto ) {
        if(dto.getAdminId() == null || dto.getAdminId().isEmpty()) {
        	
        	return ResponseEntity.ok("Admin Is Missing (Admin Id)");
        }
        return leadService.createLead(dto, "Form Genrated");
    }


}
