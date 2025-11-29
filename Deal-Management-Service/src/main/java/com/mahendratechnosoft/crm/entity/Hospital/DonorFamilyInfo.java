package com.mahendratechnosoft.crm.entity.Hospital;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DonorFamilyInfo {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String donorFamailyId;
	private String donorId;
	private int brotherAge;
	private String brotherProfession;
	private int brotherKidsCount;
	private String brotherIllness;
	private int sisterAge;
	private String sisterProfession;
	private String sisterKidsCount;
	private String sisterIllness;
	
//	@ManyToOne
//	@JoinColumn(name = "donor_id")
//	private Donors donor;
	
	public DonorFamilyInfo(String donorFamailyId, String donorId, int brotherAge, String brotherProfession,
			int brotherKidsCount, String brotherIllness, int sisterAge, String sisterProfession, String sisterKidsCount,
			String sisterIllness) {
		super();
		this.donorFamailyId = donorFamailyId;
		this.donorId = donorId;
		this.brotherAge = brotherAge;
		this.brotherProfession = brotherProfession;
		this.brotherKidsCount = brotherKidsCount;
		this.brotherIllness = brotherIllness;
		this.sisterAge = sisterAge;
		this.sisterProfession = sisterProfession;
		this.sisterKidsCount = sisterKidsCount;
		this.sisterIllness = sisterIllness;
	}
	public DonorFamilyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDonorFamailyId() {
		return donorFamailyId;
	}
	public void setDonorFamailyId(String donorFamailyId) {
		this.donorFamailyId = donorFamailyId;
	}
	public String getDonorId() {
		return donorId;
	}
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	public int getBrotherAge() {
		return brotherAge;
	}
	public void setBrotherAge(int brotherAge) {
		this.brotherAge = brotherAge;
	}
	public String getBrotherProfession() {
		return brotherProfession;
	}
	public void setBrotherProfession(String brotherProfession) {
		this.brotherProfession = brotherProfession;
	}
	public int getBrotherKidsCount() {
		return brotherKidsCount;
	}
	public void setBrotherKidsCount(int brotherKidsCount) {
		this.brotherKidsCount = brotherKidsCount;
	}
	public String getBrotherIllness() {
		return brotherIllness;
	}
	public void setBrotherIllness(String brotherIllness) {
		this.brotherIllness = brotherIllness;
	}
	public int getSisterAge() {
		return sisterAge;
	}
	public void setSisterAge(int sisterAge) {
		this.sisterAge = sisterAge;
	}
	public String getSisterProfession() {
		return sisterProfession;
	}
	public void setSisterProfession(String sisterProfession) {
		this.sisterProfession = sisterProfession;
	}
	public String getSisterKidsCount() {
		return sisterKidsCount;
	}
	public void setSisterKidsCount(String sisterKidsCount) {
		this.sisterKidsCount = sisterKidsCount;
	}
	public String getSisterIllness() {
		return sisterIllness;
	}
	public void setSisterIllness(String sisterIllness) {
		this.sisterIllness = sisterIllness;
	}
	
	
}
