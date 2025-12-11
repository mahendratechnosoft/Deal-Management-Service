package com.mahendratechnosoft.crm.dto.Hospital;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mahendratechnosoft.crm.entity.Hospital.DonorBloodReport;
import com.mahendratechnosoft.crm.entity.Hospital.DonorFamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;
import com.mahendratechnosoft.crm.entity.Hospital.SemenReport;

public class DonarInfoDto {
	
	@JsonIgnoreProperties({"selfeImage","selfeImageData","fullLengthImage","fullLengthImageData"})
	private Donors donors;
	private List<DonorFamilyInfo> donorFamilyInfos;
	
	@JsonIgnoreProperties({"attachmentFile", "attachmentFileName", "attachmentFileType"})
	private DonorBloodReport donorBloodReport;
	
	@JsonIgnoreProperties({"attachmentFile", "attachmentFileName", "attachmentFileType"})
	private SemenReport semenReport;
	
	
	private SampleReport sampleReport;
	public Donors getDonors() {
		return donors;
	}
	public void setDonors(Donors donors) {
		this.donors = donors;
	}
	
	public List<DonorFamilyInfo> getDonorFamilyInfos() {
		return donorFamilyInfos;
	}
	public void setDonorFamilyInfos(List<DonorFamilyInfo> donorFamilyInfos) {
		this.donorFamilyInfos = donorFamilyInfos;
	}
	public DonorBloodReport getDonorBloodReport() {
		return donorBloodReport;
	}
	public void setDonorBloodReport(DonorBloodReport donorBloodReport) {
		this.donorBloodReport = donorBloodReport;
	}
	public SemenReport getSemenReport() {
		return semenReport;
	}
	public void setSemenReport(SemenReport semenReport) {
		this.semenReport = semenReport;
	}
	public SampleReport getSampleReport() {
		return sampleReport;
	}
	public void setSampleReport(SampleReport sampleReport) {
		this.sampleReport = sampleReport;
	}
	
	
}
