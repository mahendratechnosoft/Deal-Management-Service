package com.mahendratechnosoft.crm.dto.Hospital;

public class FamilyInfoDto {

	private String familyInfoId;
	 private String uin;
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
	 public FamilyInfoDto(String familyInfoId, String uin) {
		super();
		this.familyInfoId = familyInfoId;
		this.uin = uin;
	 }
	 public FamilyInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	 }
	 
}
