package com.github.sunlong.hellomonitor.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: sunlong
 * Date: 13-2-18
 * Time: 上午11:03
 */
@Entity
@JsonIgnoreProperties({"resource"})
public class Action {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Length(max = 50)
    private String name;

    @Column(length = 200)
    @Length(max = 200)
    private String description;

    @ManyToOne
    @JoinColumn (name="resource_id")
    @NotNull
    private Resource resource;

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

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
