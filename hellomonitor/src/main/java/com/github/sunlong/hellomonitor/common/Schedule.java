package com.github.sunlong.hellomonitor.common;

import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DeviceAndDataSource;

/**
 * User: sunlong
 * Date: 13-5-9
 * Time: 下午1:24
 */
public class Schedule implements Runnable{
    private DeviceAndDataSource deviceAndDataSource;

    public Schedule(DeviceAndDataSource deviceAndDataSource) {
        this.deviceAndDataSource = deviceAndDataSource;
    }

    @Override
    public void run() {
        try {
            deviceAndDataSource.collect();//收集数据
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
