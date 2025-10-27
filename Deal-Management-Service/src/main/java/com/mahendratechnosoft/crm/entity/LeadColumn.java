package com.mahendratechnosoft.crm.entity;


import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;

@Entity
public class LeadColumn {

    @Id
    private String id;
    private long companyId;
    private List<ColumnDefinition> columns;

    public static class ColumnDefinition {
        private String name;
        private int sequence;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getSequence() { return sequence; }
        public void setSequence(int sequence) { this.sequence = sequence; }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<ColumnDefinition> getColumns() { return columns; }
    public void setColumns(List<ColumnDefinition> columns) { this.columns = columns; }
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
    
}
