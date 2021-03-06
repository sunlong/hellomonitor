package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 设备和模板绑带渥的时候会创建属于这个设备的新的模板、数据源、数据点（通过复制的方式）
 * 修改一个设备的模板信息不会影响到真正的模板
 * 至于修改模板要不要反应到现有的设备上，这个待定
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午8:40
 */
@Entity
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @Embedded
    private DeviceProperty deviceProperty;

    @ManyToOne
    @JoinColumn(name = "device_class_id")
    private DeviceClass deviceClass;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", orphanRemoval = true)
    private Set<Template> templates = new HashSet<Template>();

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

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public DeviceClass getDeviceClass() {
        return deviceClass;
    }

    public void setDeviceClass(DeviceClass deviceClass) {
        this.deviceClass = deviceClass;
    }

    public DeviceProperty getDeviceProperty() {
        return deviceProperty;
    }

    public void setDeviceProperty(DeviceProperty deviceProperty) {
        this.deviceProperty = deviceProperty;
    }

    public void validate() {

    }

    public void addTemplate(Template template) {
        Template tp = new Template();
        tp.setName(template.getName());
        tp.setDeviceClass(template.getDeviceClass());

        //复制ds
        for(DataSource ds : template.getDataSources()){
            tp.addDataSource(ds);
        }

        //复制graphs
        for(Graph graph: template.getGraphs()){
            tp.addGraph(graph);
        }

        tp.setDevice(this);
        templates.add(tp);
    }

    public void copy(Device device) {
        this.name = device.getName();
        this.deviceClass = device.getDeviceClass();
        this.deviceProperty = device.getDeviceProperty();
    }
}
