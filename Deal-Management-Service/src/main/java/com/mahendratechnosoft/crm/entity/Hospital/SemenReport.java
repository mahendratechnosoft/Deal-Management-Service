package com.mahendratechnosoft.crm.entity.Hospital;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
@Entity
public class SemenReport {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String sampleReportId;
	private String donorId;
	private String sampleId;
	private LocalDateTime dateAndTime;
	private String  media;
	private double volumne;
	private String spermConcentration;
	private double million;
	private double progressiveMotilityA;
	private double progressiveMotilityB;
	private double progressiveMotilityC;
	private String morphology;
	private String abnormality;
	private String stage;
	private String msc;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] attachmentFile;
    private String attachmentFileName;
    private String attachmentFileType;
	
//	@ManyToOne
//	@JoinColumn(name = "donor_id")
//	private Donors donor;
	
	public SemenReport() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SemenReport(String sampleReportId, String sampleId, LocalDateTime dateAndTime, String media, double volumne,
			String spermConcentration, double million, double progressiveMotilityA, String morphology,
			String abnormality) {
		super();
		this.sampleReportId = sampleReportId;
		this.sampleId = sampleId;
		this.dateAndTime = dateAndTime;
		this.media = media;
		this.volumne = volumne;
		this.spermConcentration = spermConcentration;
		this.million = million;
		this.progressiveMotilityA = progressiveMotilityA;
		this.morphology = morphology;
		this.abnormality = abnormality;
	}


	public String getSampleReportId() {
		return sampleReportId;
	}


	public void setSampleReportId(String sampleReportId) {
		this.sampleReportId = sampleReportId;
	}


	public String getSampleId() {
		return sampleId;
	}


	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}


	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}


	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
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


	public double getMillion() {
		return million;
	}


	public void setMillion(double million) {
		this.million = million;
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


	public String getMsc() {
		return msc;
	}


	public void setMsc(String msc) {
		this.msc = msc;
	}
	
	
}
