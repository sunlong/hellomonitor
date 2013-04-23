package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:27
 */
@Entity
public class Graph {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
