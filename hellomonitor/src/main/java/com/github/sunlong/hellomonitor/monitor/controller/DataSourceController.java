package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.*;
import com.github.sunlong.hellomonitor.monitor.service.DataSourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        dataSourceService.delete(id);
        return new Result();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Integer id, Model model) throws AppException {
        DataSource dataSource = dataSourceService.find(id);
        model.addAttribute("dataSource", dataSource);
        model.addAttribute("dataSourceName", dataSource.getClass().getSimpleName());
        return "monitor/datasource/update";
    }

    @RequestMapping(value = "/updateWmiDataSource", method = RequestMethod.POST)
    @ResponseBody
    public Result updateWmiDataSource(WmiDataSource dataSource) throws AppException {
        dataSourceService.update(dataSource);
        return new Result();
    }

    @RequestMapping(value = "/updateSnmpDataSource", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSnmpDataSource(SnmpDataSource dataSource) throws AppException {
        dataSourceService.update(dataSource);
        return new Result();
    }

    @RequestMapping(value = "/updateCmdDataSource", method = RequestMethod.POST)
    @ResponseBody
    public Result updateCmdDataSource(CommandDataSource dataSource) throws AppException {
        dataSourceService.update(dataSource);
        return new Result();
    }
}
