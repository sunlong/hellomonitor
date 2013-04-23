package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:27
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="data_source_type")
public class DataSource {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Integer id;

    @Column(length = 32, nullable = false)
    protected String name;

    protected Integer collectionInterval;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dataSource", orphanRemoval = true)
    protected Set<DataPoint> dataPoints;

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
}
