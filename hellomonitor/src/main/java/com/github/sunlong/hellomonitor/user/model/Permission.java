package com.github.sunlong.hellomonitor.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: sunlong
 * Date: 13-1-31
 * Time: 下午3:35
 */
@Entity
@JsonIgnoreProperties({"role"})
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 100, nullable = false)
    @NotBlank
    @Length(max = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;

    public Permission(Role role, String strPermission) {
        this.role = role;
        this.name = strPermission;
    }

    public Permission(){
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
