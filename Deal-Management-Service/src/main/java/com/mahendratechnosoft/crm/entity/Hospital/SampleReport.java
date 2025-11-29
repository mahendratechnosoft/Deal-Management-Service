package com.mahendratechnosoft.crm.entity.Hospital;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class SampleReport {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String sampleReportId;
	private String donorId;
	private String tankNo;
	private String caneNo;
	private String canisterNo;
	private int numberOfVials;
	private String remarks;
	
//	@ManyToOne
//	@JoinColumn(name = "donor_id")
//	private Donors donor;

	public SampleReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SampleReport(String sampleReportId, String tankNo, String caneNo, String canisterNo, int numberOfVials,
			String remarks) {
		super();
		this.sampleReportId = sampleReportId;
		this.tankNo = tankNo;
		this.caneNo = caneNo;
		this.canisterNo = canisterNo;
		this.numberOfVials = numberOfVials;
		this.remarks = remarks;
	}



	public String getSampleReportId() {
		return sampleReportId;
	}

	public void setSampleReportId(String sampleReportId) {
		this.sampleReportId = sampleReportId;
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

	

}
