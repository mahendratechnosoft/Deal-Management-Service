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
    private String donarName;
    private String donarUin;
    private String familyUin;
    private String husbandName;
    private String wifeName;
    
    
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

	public String getDonarName() {
		return donarName;
	}

	public void setDonarName(String donarName) {
		this.donarName = donarName;
	}

	public String getDonarUin() {
		return donarUin;
	}

	public void setDonarUin(String donarUin) {
		this.donarUin = donarUin;
	}

	public String getFamilyUin() {
		return familyUin;
	}

	public void setFamilyUin(String familyUin) {
		this.familyUin = familyUin;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}
}
