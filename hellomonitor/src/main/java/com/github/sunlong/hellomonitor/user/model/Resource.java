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
 * Time: 下午2:31
 */
@Entity
public class Resource {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Length(max = 50)
    private String name;

    @Column(length = 200)
    @Length(max = 200)
    private String description;

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="resource", orphanRemoval = true)
    private List<Action> actions;

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

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<Resource>> failures = validator.validate(this);
        if(!failures.isEmpty()){
            throw new AppException(MessageCode.VALIDATION_ERROR, failures.iterator().next().getMessage());
        }
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
