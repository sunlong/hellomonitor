package com.github.sunlong.hellomonitor.common;

import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import com.github.sunlong.hellomonitor.monitor.model.DeviceAndDataSource;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: sunlong
 * Date: 13-5-9
 * Time: 下午1:18
 */
public class ScheduleUtil {
    private ScheduleUtil(){}

    public static ScheduleUtil getInstance(){
        return ScheduleUtilHolder.INSTANCE;
    }

    private static class ScheduleUtilHolder{
        private static final ScheduleUtil INSTANCE = new ScheduleUtil();
    }

    private final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(10);
    private final ConcurrentHashMap<Integer, ScheduledFuture<?> > map = new ConcurrentHashMap<Integer, ScheduledFuture<?> >();

    public void scheduleAtFixedRate(Device device, DataSource dataSource){
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(new Schedule(new DeviceAndDataSource(device, dataSource)), 0, dataSource.getCollectionInterval(), TimeUnit.SECONDS);
        map.put(dataSource.getId(), future);
    }

    public void shutdown() {
        executorService.shutdown();
    }


    public void cancel(Integer id) {
        map.get(id).cancel(false);
    }
}
