package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DataPoint;
import com.github.sunlong.hellomonitor.monitor.service.DataPointService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午2:21
 */
@Controller
@RequestMapping("/dataPoint")
public class DataPointController {
    @Resource
    private DataPointService dataPointService;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        dataPointService.delete(id);
        return new Result();
    }

    @RequestMapping(value = "/show")
    @ResponseBody
    public Result show(Integer id) throws AppException {
        return new Result(true, dataPointService.find(id));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(DataPoint graphPoint) throws AppException {
        dataPointService.update(graphPoint);
        return new Result();
    }
}
