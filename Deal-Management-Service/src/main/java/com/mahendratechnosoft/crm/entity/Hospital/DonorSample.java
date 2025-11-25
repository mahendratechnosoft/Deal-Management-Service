package com.mahendratechnosoft.crm.entity.Hospital;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class DonorSample {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String donorSampleId;
	private String sampleReportId;
	private String donorId;
	private String tankNo;
	private String caneNo;
	private String canisterNo;
	private int numberOfVials;
	private String remarks;

	public DonorSample() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DonorSample(String donorSampleId, String tankNo, String caneNo, String canisterNo, int numberOfVials,
			String remarks) {
		super();
		this.donorSampleId = donorSampleId;
		this.tankNo = tankNo;
		this.caneNo = caneNo;
		this.canisterNo = canisterNo;
		this.numberOfVials = numberOfVials;
		this.remarks = remarks;
	}

	public String getDonorSampleId() {
		return donorSampleId;
	}

	public void setDonorSampleId(String donorSampleId) {
		this.donorSampleId = donorSampleId;
	}

	public String getTankNo() {
		return tankNo;
	}

	public void setTankNo(String tankNo) {
		this.tankNo = tankNo;
	}

	public String getCaneNo() {
		return caneNo;
	}

	public void setCaneNo(String caneNo) {
		this.caneNo = caneNo;
	}

	public String getCanisterNo() {
		return canisterNo;
	}

	public void setCanisterNo(String canisterNo) {
		this.canisterNo = canisterNo;
	}

	public int getNumberOfVials() {
		return numberOfVials;
	}

	public void setNumberOfVials(int numberOfVials) {
		this.numberOfVials = numberOfVials;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}

	public String getSampleReportId() {
		return sampleReportId;
	}

	public void setSampleReportId(String sampleReportId) {
		this.sampleReportId = sampleReportId;
	}
	
	

}
