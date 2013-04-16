package com.github.sunlong.hellomonitor.user.model;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午2:30
 */
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 50, nullable = false)
    @NotBlank
    @Length(max = 50)
    private String username;

    @Column(length = 50)
    private String nickname;

    @Column(length = 40, nullable = false)
    @NotBlank
    @Length(max = 40)
    private String password;

    @Column(length = 50)
    @NotBlank
    @Length(max = 50)
    private String email;

    @NotNull
    private Date createdDate;

    private Date lastLoginDate;

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(length = 11)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;

    @ManyToOne
    @JoinColumn (name="user_group_id")
    @NotNull
    private UserGroup userGroup;

    private String salt;

    public User(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(){
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<User>> failures = validator.validate(this);
        if(!failures.isEmpty()){
            throw new AppException(MessageCode.VALIDATION_ERROR, failures.iterator().next().getMessage());
        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", role=" + role +
                ", userGroup=" + userGroup +
                '}';
    }
}
