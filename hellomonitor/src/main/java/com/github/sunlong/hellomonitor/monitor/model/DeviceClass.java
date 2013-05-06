package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import javax.validation.Validator;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午8:43
 */
@Entity
public class DeviceClass {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn (name="parent_id")
    private DeviceClass parent;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy="parent", orphanRemoval = true)
    private Set<DeviceClass> children;

    @OneToMany(mappedBy="deviceClass")
    private Set<Template> templates;

    @OneToMany(mappedBy = "deviceClass")
    private Set<Device> devices;

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

    public DeviceClass getParent() {
        return parent;
    }

    public void setParent(DeviceClass parent) {
        this.parent = parent;
    }

    public Set<DeviceClass> getChildren() {
        return children;
    }

    public void setChildren(Set<DeviceClass> children) {
        this.children = children;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public void validate(Validator validator) {


    }
}
