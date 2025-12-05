package com.mahendratechnosoft.crm.entity.Hospital;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class FamilyVialAllocation {
	@Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String allocationId;

    private String familyInfoId; 
    private String donorId;
    private int vialsAssignedCount;
    private LocalDateTime allocationDate;
    
    @PrePersist
	protected void onCreate() {
		this.allocationDate = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}
    
	public String getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}
	public String getFamilyInfoId() {
		return familyInfoId;
	}
	public void setFamilyInfoId(String familyInfoId) {
		this.familyInfoId = familyInfoId;
	}
	public String getDonorId() {
		return donorId;
	}
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	public int getVialsAssignedCount() {
		return vialsAssignedCount;
	}
	public void setVialsAssignedCount(int vialsAssignedCount) {
		this.vialsAssignedCount = vialsAssignedCount;
	}
	public LocalDateTime getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(LocalDateTime allocationDate) {
		this.allocationDate = allocationDate;
	}
    
}
