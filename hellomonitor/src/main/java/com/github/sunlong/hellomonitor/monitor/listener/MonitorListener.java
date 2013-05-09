package com.github.sunlong.hellomonitor.monitor.listener;

import com.github.sunlong.hellomonitor.common.DeviceAndDataSourceMap;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import com.github.sunlong.hellomonitor.monitor.model.DeviceAndDataSource;
import com.github.sunlong.hellomonitor.monitor.model.Template;
import com.github.sunlong.hellomonitor.monitor.service.DeviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:47
 */
public class MonitorListener implements ServletContextListener {
    private final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(10);
    private ConcurrentHashMap<Integer, ScheduledFuture<?> > map = new ConcurrentHashMap<Integer, ScheduledFuture<?> >();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        DeviceService deviceService = (DeviceService)context.getBean("deviceService");
        List<Device> devices = deviceService.listAll();
        for (Device device : devices){
            Set<Template> templates = device.getTemplates();
            for(Template template: templates){
                Set<DataSource> dataSources = template.getDataSources();
                for(DataSource dataSource: dataSources){
                    ScheduledFuture<?> future = executorService.scheduleAtFixedRate(new Schedule(new DeviceAndDataSource(device, dataSource)), 0, dataSource.getCollectionInterval(), TimeUnit.SECONDS);
                    map.put(dataSource.getId(), future);
                    //DeviceAndDataSourceMap.put(new DeviceAndDataSource(device, dataSource));
                }
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        executorService.shutdown();
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
