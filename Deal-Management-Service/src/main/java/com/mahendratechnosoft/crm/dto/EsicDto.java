package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.Esic;
import com.mahendratechnosoft.crm.entity.EsicContent;

public class EsicDto {
	private Esic esic;
	private List<EsicContent> esicContents;
	public Esic getEsic() {
		return esic;
	}
	public void setEsic(Esic esic) {
		this.esic = esic;
	}
	public List<EsicContent> getEsicContents() {
		return esicContents;
	}
	public void setEsicContents(List<EsicContent> esicContents) {
		this.esicContents = esicContents;
	}
	
}
