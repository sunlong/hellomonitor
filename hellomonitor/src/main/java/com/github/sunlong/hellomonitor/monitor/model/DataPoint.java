package com.github.sunlong.hellomonitor.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:28
 */
@Entity
@JsonIgnoreProperties({"dataSource"})
public class DataPoint {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 128)
    private String description;

    @ManyToOne
    @JoinColumn (name="data_source_id")
    private DataSource dataSource;

    @Column(length = 8, nullable = false)
    private String type;//COUNTER, DERIVE, ABSOLUTE, GAUGE, COMPUTE

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
