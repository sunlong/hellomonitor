package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
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

    @Column(length = 32, nullable = false)
    private String ip;


    @ManyToMany
    @JoinTable(name = "device_template",
            joinColumns = @JoinColumn(name="device_id"),
            inverseJoinColumns = @JoinColumn(name="template_id")
    )
    private Set<Template> templates;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }
}
