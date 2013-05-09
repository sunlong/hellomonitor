package com.github.sunlong.hellomonitor.monitor.model;

import org.snmp4j.mp.SnmpConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午4:21
 */
@Embeddable
public class DeviceProperty {
    @Column(length = 32, nullable = false)
    private String ip;

    @Column(length = 32)
    private String snmpCommunity;

    @Column(length = 4)
    private String snmpVersion;

    private Integer snmpPort;

    private Integer snmpTimeout;

    private Integer snmpTries;

    @Column(length = 32)
    private String username;

    @Column(length = 32)
    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public String getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(String snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public Integer getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(Integer snmpPort) {
        this.snmpPort = snmpPort;
    }

    public Integer getSnmpTimeout() {
        return snmpTimeout;
    }

    public void setSnmpTimeout(Integer snmpTimeout) {
        this.snmpTimeout = snmpTimeout;
    }

    public Integer getSnmpTries() {
        return snmpTries;
    }

    public void setSnmpTries(Integer snmpTries) {
        this.snmpTries = snmpTries;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSnmpIntVersion() {
        if(snmpVersion.equals("1")){
            return SnmpConstants.version1;
        }else if(snmpVersion.equalsIgnoreCase("2c")){
            return SnmpConstants.version2c;
        }else{
            return SnmpConstants.version3;
        }
    }
}
