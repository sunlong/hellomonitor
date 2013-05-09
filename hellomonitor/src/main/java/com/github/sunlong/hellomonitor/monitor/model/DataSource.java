package com.github.sunlong.hellomonitor.monitor.model;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:27
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="data_source_type")
public class DataSource implements Cloneable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Integer id;

    @Column(length = 32, nullable = false)
    protected String name;

    protected Integer collectionInterval;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dataSource", orphanRemoval = true)
    protected Set<DataPoint> dataPoints = new HashSet<DataPoint>();

    @ManyToOne
    @JoinColumn(name="template_id")
    private Template template;

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

    public Integer getCollectionInterval() {
        return collectionInterval;
    }

    public void setCollectionInterval(Integer collectionInterval) {
        this.collectionInterval = collectionInterval;
    }

    public Set<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(Set<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void copy(DataSource dataSource) {
        this.name = dataSource.getName();
        this.collectionInterval = dataSource.getCollectionInterval();
    }

    public void collect(DeviceProperty deviceProperty) throws AppException {
        throw new AppException(MessageCode.METHOD_NOT_OVERRIDE_ERROR, "DataSource.collect");
    }

    @Override
    public DataSource clone() throws CloneNotSupportedException {
        DataSource dataSource = (DataSource)super.clone();
        dataSource.setId(null);

        dataSource.dataPoints = new HashSet<DataPoint>();
        for(DataPoint dataPoint: this.dataPoints){
            DataPoint dp = dataPoint.clone();
            dp.setDataSource(this);
            dataSource.dataPoints.add(dp);
        }
        return dataSource;
    }
}
