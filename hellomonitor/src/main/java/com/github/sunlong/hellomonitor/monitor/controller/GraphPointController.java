package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.service.GraphPointService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 上午11:50
 */
@Controller
@RequestMapping("/graphPoint")
public class GraphPointController {
    @Resource
    private GraphPointService graphPointService;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        graphPointService.delete(id);
        return new Result();
    }
}
