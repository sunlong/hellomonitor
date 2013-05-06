package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DataPoint;
import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import com.github.sunlong.hellomonitor.monitor.service.DataSourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-4-26
 * Time: 下午3:18
 */
@Controller
@RequestMapping("/dataSource")
public class DataSourceController {
    @Resource
    private DataSourceService dataSourceService;

    @RequestMapping("/listDataPoints")
    @ResponseBody
    public Result listDataPoints(Integer id) throws AppException {
        return new Result(true, dataSourceService.find(id).getDataPoints());
    }

    @RequestMapping(value = "/addDataPoint")
    @ResponseBody
    public Result addDataPoint(DataPoint dataPoint) throws AppException {
        dataSourceService.createDataPoint(dataPoint);
        return new Result();
    }
}
