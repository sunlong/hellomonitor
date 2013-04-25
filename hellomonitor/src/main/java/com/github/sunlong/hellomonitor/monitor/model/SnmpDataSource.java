package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.Entity;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:22
 */
@Entity
public class SnmpDataSource extends DataSource {
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
