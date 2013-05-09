package com.github.sunlong.hellomonitor.monitor.model;

import com.github.sunlong.hellomonitor.exception.AppException;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午4:14
 */
public class DeviceAndDataSource {
    private DeviceProperty deviceProperty;
    private DataSource dataSource;

    public DeviceAndDataSource(Device device, DataSource dataSource){
        this.deviceProperty = device.getDeviceProperty();
        this.dataSource = dataSource;
    }

    public Integer getCollectionInterval() {
        return dataSource.getCollectionInterval();
    }

    public String getName() {
        return dataSource.getName();
    }

    public void collect() throws AppException {
        dataSource.collect(deviceProperty);
    }
}
