package com.mahendratechnosoft.crm.entity.Hospital;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class SemenEnquiry {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String semenEnquiryId;
	private String adminId;
	private String employeeId;
	private String createdBy;
	
	//personal information
	private String email;
	private String name;
	private String phoneNumber;
	private Date dateOfBirth;
	private String marriedStatus; // Yes/No
	private int age;
	private double height;
	private double weight;
	private String adharCardNo;
	private String bloodGroup;
	@Column(length = 5000)
	private String address;
	private String city;
	private String pincode;
	private String education;
	private String profession;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fullLengthImage;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] selfeImage;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}

	public String getSemenEnquiryId() {
		return semenEnquiryId;
	}

	public void setSemenEnquiryId(String semenEnquiryId) {
		this.semenEnquiryId = semenEnquiryId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMarriedStatus() {
		return marriedStatus;
	}

	public void setMarriedStatus(String marriedStatus) {
		this.marriedStatus = marriedStatus;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getAdharCardNo() {
		return adharCardNo;
	}

	public void setAdharCardNo(String adharCardNo) {
		this.adharCardNo = adharCardNo;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public byte[] getFullLengthImage() {
		return fullLengthImage;
	}

	public void setFullLengthImage(byte[] fullLengthImage) {
		this.fullLengthImage = fullLengthImage;
	}

	public byte[] getSelfeImage() {
		return selfeImage;
	}

	public void setSelfeImage(byte[] selfeImage) {
		this.selfeImage = selfeImage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
