package com.github.sunlong.hellomonitor.log.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户操作日志
 * User: sunlong
 * Date: 13-2-28
 * Time: 上午9:02
 */
@Entity
public class UserLog {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 256, nullable = false)
    @NotBlank
    @Length(max = 256)
    private String message;

    @Column(length = 32, nullable = false)
    @NotBlank
    @Length(max = 32)
    private String ip;

    @Column(length = 32, nullable = false)
    @NotBlank
    @Length(max = 32)
    private String username;

    @Column(nullable = false)
    @NotNull
    private Date createdDate;

    public UserLog(String message, String username, String ip) {
        this.message = message;
        this.username = username;
        this.createdDate = new Date();
        this.ip = ip;
    }

    public UserLog(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
