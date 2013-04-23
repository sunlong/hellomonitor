package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:28
 */
@Entity
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
}