package com.mahendratechnosoft.crm.entity;

import java.util.List;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.*;

@Entity
public class LeadColumn {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    private long companyId;

    @ElementCollection
    @CollectionTable(name = "lead_column_definitions", joinColumns = @JoinColumn(name = "lead_column_id"))
    private List<ColumnDefinition> columns;

    @Embeddable
    public static class ColumnDefinition {
        private String name;
        private int sequence;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getSequence() { return sequence; }
        public void setSequence(int sequence) { this.sequence = sequence; }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public long getCompanyId() { return companyId; }
    public void setCompanyId(long companyId) { this.companyId = companyId; }

    public List<ColumnDefinition> getColumns() { return columns; }
    public void setColumns(List<ColumnDefinition> columns) { this.columns = columns; }
}
