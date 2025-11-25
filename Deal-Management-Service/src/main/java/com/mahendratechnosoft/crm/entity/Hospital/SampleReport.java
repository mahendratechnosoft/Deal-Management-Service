package com.mahendratechnosoft.crm.entity.Hospital;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class SampleReport {
	
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
	private String progressiveMotility;
	private String morphology;
	private String abnormality;
	
	
	public SampleReport() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SampleReport(String sampleReportId, String sampleId, LocalDateTime dateAndTime, String media, double volumne,
			String spermConcentration, double million, String progressiveMotility, String morphology,
			String abnormality) {
		super();
		this.sampleReportId = sampleReportId;
		this.sampleId = sampleId;
		this.dateAndTime = dateAndTime;
		this.media = media;
		this.volumne = volumne;
		this.spermConcentration = spermConcentration;
		this.million = million;
		this.progressiveMotility = progressiveMotility;
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


	public String getProgressiveMotility() {
		return progressiveMotility;
	}


	public void setProgressiveMotility(String progressiveMotility) {
		this.progressiveMotility = progressiveMotility;
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
	
	
	
	
	
	

}
