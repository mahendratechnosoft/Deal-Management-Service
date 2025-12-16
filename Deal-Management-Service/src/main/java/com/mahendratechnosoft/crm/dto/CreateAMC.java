package com.mahendratechnosoft.crm.dto;

import java.time.LocalDate;

import com.mahendratechnosoft.crm.entity.AMC;
import com.mahendratechnosoft.crm.entity.AMCDomainHistory;
import com.mahendratechnosoft.crm.entity.AMCGsuitHistory;
import com.mahendratechnosoft.crm.entity.AMCHistory;

public class CreateAMC {
	
	private AMC amcInfo;
	
	private AMCHistory amcHistoryInfo;
	
	private AMCDomainHistory amcDomainHistoryInfo;
	
	private AMCGsuitHistory amcGsuitHistory;
	

	public AMC getAmcInfo() {
		return amcInfo;
	}

	public void setAmcInfo(AMC amcInfo) {
		this.amcInfo = amcInfo;
	}

	public AMCHistory getAmcHistoryInfo() {
		return amcHistoryInfo;
	}

	public void setAmcHistoryInfo(AMCHistory amcHistoryInfo) {
		this.amcHistoryInfo = amcHistoryInfo;
	}

	public AMCDomainHistory getAmcDomainHistoryInfo() {
		return amcDomainHistoryInfo;
	}

	public void setAmcDomainHistoryInfo(AMCDomainHistory amcDomainHistoryInfo) {
		this.amcDomainHistoryInfo = amcDomainHistoryInfo;
	}

	public AMCGsuitHistory getAmcGsuitHistory() {
		return amcGsuitHistory;
	}

	public void setAmcGsuitHistory(AMCGsuitHistory amcGsuitHistory) {
		this.amcGsuitHistory = amcGsuitHistory;
	}
	
	
}
