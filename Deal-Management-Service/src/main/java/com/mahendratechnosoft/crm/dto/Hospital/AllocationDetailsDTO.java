package com.mahendratechnosoft.crm.dto.Hospital;


import java.time.LocalDateTime;

public class AllocationDetailsDTO {

    // --- From FamilyVialAllocation (Context) ---
    private String allocationId;
    private int vialsAssignedCount;

    // --- From Donors ---
    private String donorId;
    private String donorUin;
    private String skinColor;
    private String eyeColor;
    private String education;
    private String profession;
    private String religion;

    // --- From FamilyInfo ---
    private String familyInfoId;
    private String familyUin;
    private String referHospital;
    private String referHospitalAddress;
    private String referDoctor;
    private String husbandName;
    private String wifeName;

    // --- From DonorBloodReport (Latest) ---
    private String donorBloodReportId;
    private String bloodGroup;
    private String bsl;
    private String HIV;
    private String HBSAG;
    private String VDRL;
    private String HCV;
    private String HBElectrophoresis;
    private String SRCreatinine;
    private String CMV;

    // --- From SemenReport (Latest) ---
    private String sampleReportId;
    private LocalDateTime semenReportDateAndTime;
    private String media;
    private double volumne;
    private String spermConcentration;
    private double progressiveMotilityA;
    private double progressiveMotilityB;
    private double progressiveMotilityC;
    private String morphology;
    private String abnormality;
    private double million;
	public String getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}
	public String getDonorId() {
		return donorId;
	}
	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}
	public String getDonorUin() {
		return donorUin;
	}
	public void setDonorUin(String donorUin) {
		this.donorUin = donorUin;
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
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getFamilyInfoId() {
		return familyInfoId;
	}
	public void setFamilyInfoId(String familyInfoId) {
		this.familyInfoId = familyInfoId;
	}
	public String getFamilyUin() {
		return familyUin;
	}
	public void setFamilyUin(String familyUin) {
		this.familyUin = familyUin;
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
	public String getDonorBloodReportId() {
		return donorBloodReportId;
	}
	public void setDonorBloodReportId(String donorBloodReportId) {
		this.donorBloodReportId = donorBloodReportId;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getBsl() {
		return bsl;
	}
	public void setBsl(String bsl) {
		this.bsl = bsl;
	}
	public String getHIV() {
		return HIV;
	}
	public void setHIV(String hIV) {
		HIV = hIV;
	}
	public String getHBSAG() {
		return HBSAG;
	}
	public void setHBSAG(String hBSAG) {
		HBSAG = hBSAG;
	}
	public String getVDRL() {
		return VDRL;
	}
	public void setVDRL(String vDRL) {
		VDRL = vDRL;
	}
	public String getHCV() {
		return HCV;
	}
	public void setHCV(String hCV) {
		HCV = hCV;
	}
	public String getHBElectrophoresis() {
		return HBElectrophoresis;
	}
	public void setHBElectrophoresis(String hBElectrophoresis) {
		HBElectrophoresis = hBElectrophoresis;
	}
	public String getSRCreatinine() {
		return SRCreatinine;
	}
	public void setSRCreatinine(String sRCreatinine) {
		SRCreatinine = sRCreatinine;
	}
	public String getCMV() {
		return CMV;
	}
	public void setCMV(String cMV) {
		CMV = cMV;
	}
	public String getSampleReportId() {
		return sampleReportId;
	}
	public void setSampleReportId(String sampleReportId) {
		this.sampleReportId = sampleReportId;
	}
	public LocalDateTime getSemenReportDateAndTime() {
		return semenReportDateAndTime;
	}
	public void setSemenReportDateAndTime(LocalDateTime semenReportDateAndTime) {
		this.semenReportDateAndTime = semenReportDateAndTime;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public double getVolumne() {
		return volumne;
	}
	public void setVolumne(double volumne) {
		this.volumne = volumne;
	}
	public String getSpermConcentration() {
		return spermConcentration;
	}
	public void setSpermConcentration(String spermConcentration) {
		this.spermConcentration = spermConcentration;
	}
	public double getProgressiveMotilityA() {
		return progressiveMotilityA;
	}
	public void setProgressiveMotilityA(double progressiveMotilityA) {
		this.progressiveMotilityA = progressiveMotilityA;
	}
	public double getProgressiveMotilityB() {
		return progressiveMotilityB;
	}
	public void setProgressiveMotilityB(double progressiveMotilityB) {
		this.progressiveMotilityB = progressiveMotilityB;
	}
	public double getProgressiveMotilityC() {
		return progressiveMotilityC;
	}
	public void setProgressiveMotilityC(double progressiveMotilityC) {
		this.progressiveMotilityC = progressiveMotilityC;
	}
	public String getMorphology() {
		return morphology;
	}
	public void setMorphology(String morphology) {
		this.morphology = morphology;
	}
	public String getAbnormality() {
		return abnormality;
	}
	public void setAbnormality(String abnormality) {
		this.abnormality = abnormality;
	}
	public int getVialsAssignedCount() {
		return vialsAssignedCount;
	}
	public void setVialsAssignedCount(int vialsAssignedCount) {
		this.vialsAssignedCount = vialsAssignedCount;
	}
	public double getMillion() {
		return million;
	}
	public void setMillion(double million) {
		this.million = million;
	}

    
}