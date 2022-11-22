package com.agile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "table_config")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableConfigDo {
    
    private String id;
    private String tableName;
    private String config;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    @Column(name = "config")
    public String getConfig() {
        return config;
    }
}
