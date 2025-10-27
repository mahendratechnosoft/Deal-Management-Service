package com.mahendratechnosoft.crm.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.mahendratechnosoft.crm.dto.AdminInfoDto;
import com.mahendratechnosoft.crm.dto.AdminUpdateDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.repository.AdminRepository;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private AdminRepository adminRepository;
    
    @ModelAttribute("admin")
    public Admin getCurrentlyLoggedInAdmin(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        
        String loginEmail = authentication.getName();
        return adminRepository.findByUser_LoginEmail(loginEmail)
                .orElseThrow(() -> new RuntimeException("Admin profile not found for user: " + loginEmail));
    }
    
    @GetMapping("/getAdminInfo")
    public ResponseEntity<AdminInfoDto> getAdminInfo(@ModelAttribute("admin") Admin admin) {
    	AdminInfoDto dto = new AdminInfoDto(admin);
        return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/updateAdminInfo") // Note: Different path
    public ResponseEntity<AdminInfoDto> updateAdminInfo(
            @ModelAttribute("admin") Admin adminToUpdate,
            @RequestBody AdminUpdateDto updateDto) {

        // Update entity from DTO
        adminToUpdate.setName(updateDto.getName());
        adminToUpdate.setPhone(updateDto.getPhone());
        adminToUpdate.setAddress(updateDto.getAddress());
        adminToUpdate.setCompanyName(updateDto.getCompanyName());
        adminToUpdate.setDescription(updateDto.getDescription());
        
        String base64Image = updateDto.getLogoBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
        	try {
        		byte[] logoBytes = Base64.getDecoder().decode(base64Image);
        		adminToUpdate.setLogo(logoBytes);
        	} catch (IllegalArgumentException e) {
        		throw new RuntimeException("Invalid Base64 image format", e);
        	}
        }

        Admin updatedAdmin = adminRepository.save(adminToUpdate);
        return ResponseEntity.ok(new AdminInfoDto(updatedAdmin));
    }

}
