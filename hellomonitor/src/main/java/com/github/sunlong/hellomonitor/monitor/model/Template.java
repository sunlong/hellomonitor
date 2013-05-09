package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午8:48
 */
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="device_class_id")
    private DeviceClass deviceClass;

    @ManyToOne
    @JoinColumn(name="device_id")
    private Device device;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "template", orphanRemoval = true)
    private Set<DataSource> dataSources = new HashSet<DataSource>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "template", orphanRemoval = true)
    private Set<Graph> graphs = new HashSet<Graph>();

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

    public DeviceClass getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(DeviceClass deviceClass) {
        this.deviceClass = deviceClass;
    }

    public Set<DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Set<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public Set<Graph> getGraphs() {
        return graphs;
    }

    public void setGraphs(Set<Graph> graphs) {
        this.graphs = graphs;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void validate(Validator validator) {


    }

    public void addGraph(Graph graph) {
        Graph tmp = new Graph();
        tmp.setName(graph.getName());
        //复制graph points
        for(GraphPoint gp: graph.getGraphPoints()){
            tmp.addGraphPoint(gp, dataSources);
        }
        tmp.setTemplate(this);
        graphs.add(tmp);
    }

    public void addDataSource(DataSource ds) {
        DataSource dataSource = null;
        try {
            dataSource = ds.clone();
            dataSource.setTemplate(this);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();//todo log this exception
        }

        dataSources.add(dataSource);
    }
}
