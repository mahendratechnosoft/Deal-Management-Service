package com.mahendratechnosoft.crm.dto.Hospital;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;

public class DonorResponseDto {
	
	@JsonUnwrapped
    private Donors donor;
    
    private int balancedVials;

    public DonorResponseDto(Donors donor, Integer balancedVials) {
        this.donor = donor;
        this.balancedVials = (balancedVials != null) ? balancedVials : 0;
    }

    // Getters and Setters
    public Donors getDonor() { return donor; }
    public void setDonor(Donors donor) { this.donor = donor; }
    public int getBalancedVials() { return balancedVials; }
    public void setBalancedVials(int balancedVials) { this.balancedVials = balancedVials; }

}
