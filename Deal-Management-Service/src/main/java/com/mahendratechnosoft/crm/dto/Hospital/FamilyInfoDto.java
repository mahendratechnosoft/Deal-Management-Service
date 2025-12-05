package com.mahendratechnosoft.crm.dto.Hospital;

public class FamilyInfoDto {

	private String familyInfoId;
	 private String uin;
	 private String wifeName;
	 private String husbandName;
	 public String getFamilyInfoId() {
		 return familyInfoId;
	 }
	 public void setFamilyInfoId(String familyInfoId) {
		 this.familyInfoId = familyInfoId;
	 }
	 public String getUin() {
		 return uin;
	 }
	 public void setUin(String uin) {
		 this.uin = uin;
	 }
	 public FamilyInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	 }
	 public FamilyInfoDto(String familyInfoId, String uin, String wifeName, String husbandName) {
		super();
		this.familyInfoId = familyInfoId;
		this.uin = uin;
		this.wifeName = wifeName;
		this.husbandName = husbandName;
	 }
	 public String getWifeName() {
		 return wifeName;
	 }
	 public void setWifeName(String wifeName) {
		 this.wifeName = wifeName;
	 }
	 public String getHusbandName() {
		 return husbandName;
	 }
	 public void setHusbandName(String husbandName) {
		 this.husbandName = husbandName;
	 }
	 
}
