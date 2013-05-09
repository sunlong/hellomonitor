package com.github.sunlong.hellomonitor.monitor.thread;

import com.github.sunlong.hellomonitor.common.DeviceAndDataSourceMap;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DeviceAndDataSource;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午3:07
 */
public class MonitoringThread extends Thread{
    private final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(10);
    private AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void run(){
        while (running.get()){
            if(DeviceAndDataSourceMap.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{//取出datasource进行监控
                for(DeviceAndDataSource deviceAndDataSource: DeviceAndDataSourceMap.getDataSources()){
                    executorService.scheduleAtFixedRate(new Schedule(deviceAndDataSource), 0, deviceAndDataSource.getCollectionInterval(), TimeUnit.SECONDS);
                }
            }
        }
    }

    public void stopRunning(){
        running.set(false);
    }

    private class Schedule implements Runnable{
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
}
