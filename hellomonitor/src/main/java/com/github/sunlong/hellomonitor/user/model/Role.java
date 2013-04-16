package com.github.sunlong.hellomonitor.user.model;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午2:30
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 50, nullable = false)
    @Length(max = 50)
    @NotBlank
    private String name;

    @Column(length = 200)
    @Length(max = 200)
    private String description;

    @Column(nullable = false)
    private Boolean isDefault;

    @OneToMany(mappedBy="role")
    private List<User> users;

    @OneToMany(mappedBy="role", cascade={CascadeType.ALL}, orphanRemoval = true)
    private List<Permission> permissions;

    public Role(Integer id, List<Permission> permissionList) {
        this.id = id;
        this.permissions = permissionList;
    }

    public Role(){
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDefault() {
        return isDefault;
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

    public List<User> getUsers() {
        return users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<String> getStringPermissions() {
        List<String> stringPermissions = new ArrayList<String>();
        for(Permission p : permissions){
            stringPermissions.add(p.getName());
        }
        return stringPermissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<Role>> failures = validator.validate(this);
        if(!failures.isEmpty()){
            throw new AppException(MessageCode.VALIDATION_ERROR, failures.iterator().next().getMessage());
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}