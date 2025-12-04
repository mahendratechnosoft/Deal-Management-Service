package com.mahendratechnosoft.crm.entity.Hospital;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
@Entity
public class DonorBloodReport {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String donorBloodReportId;
	private String donorId;
	private LocalDateTime reportDateTime;
	private String bloodGroup;
	private String bsl;
	private String HIV;
	private String HBSAG;
	private String VDRL;
	private String HCV;
	private String HBElectrophoresis;
	private String SRCreatinine;
	private String CMV;
	private String reportType;
	private String stage;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] attachmentFile;
    private String attachmentFileName;
    private String attachmentFileType;
	
	public DonorBloodReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DonorBloodReport(String donorBloodReportId, LocalDateTime reportDateTime, String bloodGroup, String bsl,
			String hIV, String hBSAG, String vDRL, String hCV, String hBElectrophoresis, String sRCreatinine,
			String cMV) {
		super();
		this.donorBloodReportId = donorBloodReportId;
		this.reportDateTime = reportDateTime;
		this.bloodGroup = bloodGroup;
		this.bsl = bsl;
		HIV = hIV;
		HBSAG = hBSAG;
		VDRL = vDRL;
		HCV = hCV;
		HBElectrophoresis = hBElectrophoresis;
		SRCreatinine = sRCreatinine;
		CMV = cMV;
	}

	public String getDonorBloodReportId() {
		return donorBloodReportId;
	}

	public void setDonorBloodReportId(String donorBloodReportId) {
		this.donorBloodReportId = donorBloodReportId;
	}

	public LocalDateTime getReportDateTime() {
		return reportDateTime;
	}

	public void setReportDateTime(LocalDateTime reportDateTime) {
		this.reportDateTime = reportDateTime;
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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getDonorId() {
		return donorId;
	}

	public void setDonorId(String donorId) {
		this.donorId = donorId;
	}

	public byte[] getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(byte[] attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getAttachmentFileType() {
		return attachmentFileType;
	}

	public void setAttachmentFileType(String attachmentFileType) {
		this.attachmentFileType = attachmentFileType;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	
}
