package com.mahendratechnosoft.crm.entity.Hospital;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class FamilyInfo {

    @Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
    private String familyInfoId;
    private String adminId;
    private String employeeId;
    private String uin;
    private String referHospital;
    private String referHospitalAddress;
    private String referDoctor;
    private String referDoctorContactNumber;
    private String husbandName;
    private String husbandMobile;
    private String husbandHeight;
    private String husbandWeight; 
    private String husbandBloodGroup;
    private String husbandSkinColor;
    private String husbandEyeColor;
    private String husbandReligion;
    private String husbandEducation;
    private String husbandDistrict;
    private String husbandCountry;
    @Column(length = 500)
    private String husbandGenticIllness; 
    private String wifeName;
    private String wifeMobile;
    private String wifeHeight;
    private String wifeWeight;
    private String wifeBloodGroup;
    private String wifeSkinColor;
    private String wifeEyeColor;
    private String wifeReligion;
    private String wifeEducation;
    private String wifeDistrict;
    private String wifeCountry;
    @Column(length = 500)
    private String wifeGenticIllness;
	public FamilyInfo(String familyInfoId,  String referHospital, String referHospitalAddress,
			String referDoctor, String referDoctorContactNumber, String husbandName, String husbandMobile,
			String husbandHeight, String husbandWeight, String husbandBloodGroup, String husbandSkinColor,
			String husbandEyeColor, String husbandReligion, String husbandEducation, String husbandDistrict,
			String husbandCountry, String husbandGenticIllness, String wifeName, String wifeMobile, String wifeHeight,
			String wifeWeight, String wifeBloodGroup, String wifeSkinColor, String wifeEyeColor, String wifeReligion,
			String wifeEducation, String wifeDistrict, String wifeCountry, String wifeGenticIllness,String uin) {
		super();
		this.familyInfoId = familyInfoId;
		this.referHospital = referHospital;
		this.referHospitalAddress = referHospitalAddress;
		this.referDoctor = referDoctor;
		this.referDoctorContactNumber = referDoctorContactNumber;
		this.husbandName = husbandName;
		this.husbandMobile = husbandMobile;
		this.husbandHeight = husbandHeight;
		this.husbandWeight = husbandWeight;
		this.husbandBloodGroup = husbandBloodGroup;
		this.husbandSkinColor = husbandSkinColor;
		this.husbandEyeColor = husbandEyeColor;
		this.husbandReligion = husbandReligion;
		this.husbandEducation = husbandEducation;
		this.husbandDistrict = husbandDistrict;
		this.husbandCountry = husbandCountry;
		this.husbandGenticIllness = husbandGenticIllness;
		this.wifeName = wifeName;
		this.wifeMobile = wifeMobile;
		this.wifeHeight = wifeHeight;
		this.wifeWeight = wifeWeight;
		this.wifeBloodGroup = wifeBloodGroup;
		this.wifeSkinColor = wifeSkinColor;
		this.wifeEyeColor = wifeEyeColor;
		this.wifeReligion = wifeReligion;
		this.wifeEducation = wifeEducation;
		this.wifeDistrict = wifeDistrict;
		this.wifeCountry = wifeCountry;
		this.wifeGenticIllness = wifeGenticIllness;
		this.uin=uin;
	}
	public FamilyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFamilyInfoId() {
		return familyInfoId;
	}
	public void setFamilyInfoId(String familyInfoId) {
		this.familyInfoId = familyInfoId;
	}
	public String getReferHospital() {
		return referHospital;
	}
	public void setReferHospital(String referHospital) {
		this.referHospital = referHospital;
	}
	public String getReferHospitalAddress() {
		return referHospitalAddress;
	}
	public void setReferHospitalAddress(String referHospitalAddress) {
		this.referHospitalAddress = referHospitalAddress;
	}
	public String getReferDoctor() {
		return referDoctor;
	}
	public void setReferDoctor(String referDoctor) {
		this.referDoctor = referDoctor;
	}
	public String getReferDoctorContactNumber() {
		return referDoctorContactNumber;
	}
	public void setReferDoctorContactNumber(String referDoctorContactNumber) {
		this.referDoctorContactNumber = referDoctorContactNumber;
	}
	public String getHusbandName() {
		return husbandName;
	}
	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}
	public String getHusbandMobile() {
		return husbandMobile;
	}
	public void setHusbandMobile(String husbandMobile) {
		this.husbandMobile = husbandMobile;
	}
	public String getHusbandHeight() {
		return husbandHeight;
	}
	public void setHusbandHeight(String husbandHeight) {
		this.husbandHeight = husbandHeight;
	}
	public String getHusbandWeight() {
		return husbandWeight;
	}
	public void setHusbandWeight(String husbandWeight) {
		this.husbandWeight = husbandWeight;
	}
	public String getHusbandBloodGroup() {
		return husbandBloodGroup;
	}
	public void setHusbandBloodGroup(String husbandBloodGroup) {
		this.husbandBloodGroup = husbandBloodGroup;
	}
	public String getHusbandSkinColor() {
		return husbandSkinColor;
	}
	public void setHusbandSkinColor(String husbandSkinColor) {
		this.husbandSkinColor = husbandSkinColor;
	}
	public String getHusbandEyeColor() {
		return husbandEyeColor;
	}
	public void setHusbandEyeColor(String husbandEyeColor) {
		this.husbandEyeColor = husbandEyeColor;
	}
	public String getHusbandReligion() {
		return husbandReligion;
	}
	public void setHusbandReligion(String husbandReligion) {
		this.husbandReligion = husbandReligion;
	}
	public String getHusbandEducation() {
		return husbandEducation;
	}
	public void setHusbandEducation(String husbandEducation) {
		this.husbandEducation = husbandEducation;
	}
	public String getHusbandDistrict() {
		return husbandDistrict;
	}
	public void setHusbandDistrict(String husbandDistrict) {
		this.husbandDistrict = husbandDistrict;
	}
	public String getHusbandCountry() {
		return husbandCountry;
	}
	public void setHusbandCountry(String husbandCountry) {
		this.husbandCountry = husbandCountry;
	}
	public String getHusbandGenticIllness() {
		return husbandGenticIllness;
	}
	public void setHusbandGenticIllness(String husbandGenticIllness) {
		this.husbandGenticIllness = husbandGenticIllness;
	}
	public String getWifeName() {
		return wifeName;
	}
	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}
	public String getWifeMobile() {
		return wifeMobile;
	}
	public void setWifeMobile(String wifeMobile) {
		this.wifeMobile = wifeMobile;
	}
	public String getWifeHeight() {
		return wifeHeight;
	}
	public void setWifeHeight(String wifeHeight) {
		this.wifeHeight = wifeHeight;
	}
	public String getWifeWeight() {
		return wifeWeight;
	}
	public void setWifeWeight(String wifeWeight) {
		this.wifeWeight = wifeWeight;
	}
	public String getWifeBloodGroup() {
		return wifeBloodGroup;
	}
	public void setWifeBloodGroup(String wifeBloodGroup) {
		this.wifeBloodGroup = wifeBloodGroup;
	}
	public String getWifeSkinColor() {
		return wifeSkinColor;
	}
	public void setWifeSkinColor(String wifeSkinColor) {
		this.wifeSkinColor = wifeSkinColor;
	}
	public String getWifeEyeColor() {
		return wifeEyeColor;
	}
	public void setWifeEyeColor(String wifeEyeColor) {
		this.wifeEyeColor = wifeEyeColor;
	}
	public String getWifeReligion() {
		return wifeReligion;
	}
	public void setWifeReligion(String wifeReligion) {
		this.wifeReligion = wifeReligion;
	}
	public String getWifeEducation() {
		return wifeEducation;
	}
	public void setWifeEducation(String wifeEducation) {
		this.wifeEducation = wifeEducation;
	}
	public String getWifeDistrict() {
		return wifeDistrict;
	}
	public void setWifeDistrict(String wifeDistrict) {
		this.wifeDistrict = wifeDistrict;
	}
	public String getWifeCountry() {
		return wifeCountry;
	}
	public void setWifeCountry(String wifeCountry) {
		this.wifeCountry = wifeCountry;
	}
	public String getWifeGenticIllness() {
		return wifeGenticIllness;
	}
	public void setWifeGenticIllness(String wifeGenticIllness) {
		this.wifeGenticIllness = wifeGenticIllness;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
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
    
    
}