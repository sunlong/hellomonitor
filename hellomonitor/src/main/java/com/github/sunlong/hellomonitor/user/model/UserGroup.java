package com.github.sunlong.hellomonitor.user.model;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午2:30
 */
@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Length(max = 50)
    private String name;

    @OneToMany(mappedBy="userGroup")
    private List<User> users;

    @ManyToOne
    @JoinColumn (name="parent_user_group_id")
    private UserGroup parentUserGroup;

    @OneToMany(cascade={CascadeType.REMOVE}, mappedBy="parentUserGroup")
    private List<UserGroup> subUserGroups;

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

    public UserGroup getParentUserGroup() {
        return parentUserGroup;
    }

    public void setParentUserGroup(UserGroup parentUserGroup) {
        this.parentUserGroup = parentUserGroup;
    }

    public List<UserGroup> getSubUserGroups() {
        return subUserGroups;
    }

    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<UserGroup>> failures = validator.validate(this);
        if(!failures.isEmpty()){
            throw new AppException(MessageCode.VALIDATION_ERROR, failures.iterator().next().getMessage());
        }
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentUserGroup=" + parentUserGroup +
                '}';
    }
}
