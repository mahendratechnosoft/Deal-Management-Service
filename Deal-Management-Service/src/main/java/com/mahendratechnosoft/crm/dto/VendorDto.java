package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.Vendor;
import com.mahendratechnosoft.crm.entity.VendorAttachment;

public class VendorDto {
	private Vendor vendor;
	private List<VendorAttachment> vendorAttachments;
	
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public List<VendorAttachment> getVendorAttachments() {
		return vendorAttachments;
	}
	public void setVendorAttachments(List<VendorAttachment> vendorAttachments) {
		this.vendorAttachments = vendorAttachments;
	}
}
