package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: sunlong
 * Date: 13-4-25
 * Time: 上午8:44
 */
@Entity
public class DataPointValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 16, nullable = false)
    private String value;//值的字符串表示

    @Column(length = 16, nullable = false)
    private String valuePerSecond;

    private Date createdDate;

    @ManyToOne
    @JoinColumn (name="data_point_id")
    private DataPoint dataPoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValuePerSecond() {
        return valuePerSecond;
    }

    public void setValuePerSecond(String valuePerSecond) {
        this.valuePerSecond = valuePerSecond;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public DataPoint getDataPoint() {
        return dataPoint;
    }

    public void setDataPoint(DataPoint dataPoint) {
        this.dataPoint = dataPoint;
    }
}