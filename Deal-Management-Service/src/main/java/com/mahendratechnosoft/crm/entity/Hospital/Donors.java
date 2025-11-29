package com.mahendratechnosoft.crm.entity.Hospital;

import java.sql.Date;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class Donors {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String donorId;
	private String adminId;
	private String employeeId;
	private String createdBy;
	private long createdDateTime;
	
	//personal information
	private String name;
	private int age;
	private Date dateOfBirth;
	private String adharCardNo;
	private String marriedStatus; // Yes/No
	private int maleKidsCount;
	private int femaleKidsCount;
	private double height;
	private double weight;
	private String skinColor;
	private String eyeColor;
	private String education;
	private String profession;
	private String religion;
	private String bloodGroup;
	
	@Column(length = 5000)
	private String address;
	private String city;
	private String pincode;
	private String phoneNumber;
	private String email;
	@Column(unique = true)
	private String uin;
	
	//Medical History
	private boolean hospitalAdmissionStatus;
	@Column(length = 500)
	private String hospitalAdmissionReason;
	private boolean surgeryStatus;
	@Column(length = 500)
	private String surgeryReason;
	private boolean bloodDonationStatus;
	@Column(length = 500)
	private String bloodDonationReason;
	private boolean prolongedIllnessStatus;
	@Column(length = 500)
	private String prolongedIllnessReason;
	
	private String status;
 
//	private String bloodGroup;
//	private String bsl;
//	private String HIV;
//	private String HBSAG;
//	private String VDRL;
//	private String HCV;
//	private String HBElectrophoresis;
//	private String SRCreatinine;
//	private String CMV;


	
	

	@PrePersist
	public void generateUIN() {
	    this.uin = "UIN-" + System.currentTimeMillis();  // Example: UIN-1737104525511
	}
//	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] selfeImage;
	
	@Transient
	private String selfeImageData;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] fullLengthImage;

	@Transient
	private String fullLengthImageData;

	public Donors() {
		super();
		// TODO Auto-generated constructor stub
	}

 

	public Donors(String donorId, String adminId, String employeeId, String createdBy, long createdDateTime,
			String name, int age, Date dateOfBirth, String adharCardNo, String marriedStatus, int maleKidsCount,
			int femaleKidsCount, double height, double weight, String skinColor, String eyeColor, String education,
			String profession, String religion, String address, String city, String pincode, String phoneNumber,
			String uin, boolean hospitalAdmissionStatus, String hospitalAdmissionReason, boolean surgeryStatus,
			String surgeryReason, boolean bloodDonationStatus, String bloodDonationReason,
			boolean prolongedIllnessStatus, String prolongedIllnessReason, String status,String bloodGroup,String email) {
		super();
		this.donorId = donorId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.name = name;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.adharCardNo = adharCardNo;
		this.marriedStatus = marriedStatus;
		this.maleKidsCount = maleKidsCount;
		this.femaleKidsCount = femaleKidsCount;
		this.height = height;
		this.weight = weight;
		this.skinColor = skinColor;
		this.eyeColor = eyeColor;
		this.education = education;
		this.profession = profession;
		this.religion = religion;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
		this.phoneNumber = phoneNumber;
		this.uin = uin;
		this.hospitalAdmissionStatus = hospitalAdmissionStatus;
		this.hospitalAdmissionReason = hospitalAdmissionReason;
		this.surgeryStatus = surgeryStatus;
		this.surgeryReason = surgeryReason;
		this.bloodDonationStatus = bloodDonationStatus;
		this.bloodDonationReason = bloodDonationReason;
		this.prolongedIllnessStatus = prolongedIllnessStatus;
		this.prolongedIllnessReason = prolongedIllnessReason;
		this.status = status;
		this.bloodGroup=bloodGroup;
		this.email=email;
	}



	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
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

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAdharCardNo() {
		return adharCardNo;
	}

	public void setAdharCardNo(String adharCardNo) {
		this.adharCardNo = adharCardNo;
	}

	public String getMarriedStatus() {
		return marriedStatus;
	}

	public void setMarriedStatus(String marriedStatus) {
		this.marriedStatus = marriedStatus;
	}

	public int getMaleKidsCount() {
		return maleKidsCount;
	}

	public void setMaleKidsCount(int maleKidsCount) {
		this.maleKidsCount = maleKidsCount;
	}

	public int getFemaleKidsCount() {
		return femaleKidsCount;
	}

	public void setFemaleKidsCount(int femaleKidsCount) {
		this.femaleKidsCount = femaleKidsCount;
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

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public boolean isHospitalAdmissionStatus() {
		return hospitalAdmissionStatus;
	}

	public void setHospitalAdmissionStatus(boolean hospitalAdmissionStatus) {
		this.hospitalAdmissionStatus = hospitalAdmissionStatus;
	}

	public String getHospitalAdmissionReason() {
		return hospitalAdmissionReason;
	}

	public void setHospitalAdmissionReason(String hospitalAdmissionReason) {
		this.hospitalAdmissionReason = hospitalAdmissionReason;
	}

	public boolean isSurgeryStatus() {
		return surgeryStatus;
	}

	public void setSurgeryStatus(boolean surgeryStatus) {
		this.surgeryStatus = surgeryStatus;
	}

	public String getSurgeryReason() {
		return surgeryReason;
	}

	public void setSurgeryReason(String surgeryReason) {
		this.surgeryReason = surgeryReason;
	}

	public boolean isBloodDonationStatus() {
		return bloodDonationStatus;
	}

	public void setBloodDonationStatus(boolean bloodDonationStatus) {
		this.bloodDonationStatus = bloodDonationStatus;
	}

	public String getBloodDonationReason() {
		return bloodDonationReason;
	}

	public void setBloodDonationReason(String bloodDonationReason) {
		this.bloodDonationReason = bloodDonationReason;
	}

	public boolean isProlongedIllnessStatus() {
		return prolongedIllnessStatus;
	}

	public void setProlongedIllnessStatus(boolean prolongedIllnessStatus) {
		this.prolongedIllnessStatus = prolongedIllnessStatus;
	}

	public String getProlongedIllnessReason() {
		return prolongedIllnessReason;
	}

	public void setProlongedIllnessReason(String prolongedIllnessReason) {
		this.prolongedIllnessReason = prolongedIllnessReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public byte[] getSelfeImage() {
		return selfeImage;
	}

	public void setSelfeImage(byte[] selfeImage) {
		this.selfeImage = selfeImage;
	}

	public String getSelfeImageData() {
		return selfeImageData;
	}

	public void setSelfeImageData(String selfeImageData) {
		this.selfeImageData = selfeImageData;
	}

	public byte[] getFullLengthImage() {
		return fullLengthImage;
	}

	public void setFullLengthImage(byte[] fullLengthImage) {
		this.fullLengthImage = fullLengthImage;
	}

	public String getFullLengthImageData() {
		return fullLengthImageData;
	}

	public void setFullLengthImageData(String fullLengthImageData) {
		this.fullLengthImageData = fullLengthImageData;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
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



	public String getBloodGroup() {
		return bloodGroup;
	}



	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
