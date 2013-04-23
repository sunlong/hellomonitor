package com.github.sunlong.hellomonitor.monitor.listener;

import com.github.sunlong.hellomonitor.monitor.service.DeviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
