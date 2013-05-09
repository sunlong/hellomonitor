package com.github.sunlong.hellomonitor.common;

import com.github.sunlong.hellomonitor.monitor.model.DeviceAndDataSource;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午2:51
 */
public class DeviceAndDataSourceMap {
    private final static ConcurrentHashMap<String, DeviceAndDataSource> map = new ConcurrentHashMap<String, DeviceAndDataSource>();

    public static void put(DeviceAndDataSource deviceAndDataSource){
        map.putIfAbsent(deviceAndDataSource.getName(), deviceAndDataSource);
    }

    public static void remove(String key){
        map.remove(key);
    }

    public static boolean isEmpty(){
        return map.isEmpty();
    }

    public static Collection<DeviceAndDataSource> getDataSources() {
        return map.values();
    }
}
