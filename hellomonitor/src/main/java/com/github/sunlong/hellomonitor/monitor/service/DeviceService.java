package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.monitor.dao.IDeviceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午11:07
 */
@Service
public class DeviceService {
    private IDeviceDao deviceDao;

    @Resource
    public void setDeviceDao(IDeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }
}
