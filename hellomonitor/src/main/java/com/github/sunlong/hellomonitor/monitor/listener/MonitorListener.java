package com.github.sunlong.hellomonitor.monitor.listener;

import com.github.sunlong.hellomonitor.common.ScheduleUtil;
import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import com.github.sunlong.hellomonitor.monitor.model.Template;
import com.github.sunlong.hellomonitor.monitor.service.DeviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:47
 */
public class MonitorListener implements ServletContextListener {

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
                    ScheduleUtil.getInstance().scheduleAtFixedRate(device, dataSource);
                }
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ScheduleUtil.getInstance().shutdown();
    }
}
