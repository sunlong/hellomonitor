package com.github.sunlong.hellomonitor.monitor.model;

import com.github.sunlong.hellomonitor.common.SnmpUtil;
import com.github.sunlong.hellomonitor.exception.AppException;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.IOException;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:22
 */
@Entity
public class SnmpDataSource extends DataSource {
    @Column(length = 64)
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public void copy(DataSource dataSource){
        super.copy(dataSource);
        SnmpDataSource snmpDataSource = (SnmpDataSource) dataSource;
        this.oid = snmpDataSource.getOid();
    }

    @Override
    public void collect(DeviceProperty deviceProperty){
        SnmpUtil snmpUtil = SnmpUtil.getInstance();
        try {
            snmpUtil.init(deviceProperty);
            snmpUtil.send(deviceProperty.getIp(), oid);
        } catch (IOException e) {

        }
    }
}
