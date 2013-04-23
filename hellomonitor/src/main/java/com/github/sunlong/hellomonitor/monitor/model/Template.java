package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "templates")
    private Set<Device> devices;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "template", orphanRemoval = true)
    private Set<DataSource> dataSources;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "template", orphanRemoval = true)
    private Set<Graph> graphs;
}
