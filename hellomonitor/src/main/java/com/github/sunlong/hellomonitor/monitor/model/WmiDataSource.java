package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.Entity;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:40
 */
@Entity
public class WmiDataSource extends DataSource{
    @Override
    public void collect(DeviceProperty deviceProperty){

    }

    @Override
    public WmiDataSource clone() throws CloneNotSupportedException {
        return (WmiDataSource)super.clone();
    }
}
