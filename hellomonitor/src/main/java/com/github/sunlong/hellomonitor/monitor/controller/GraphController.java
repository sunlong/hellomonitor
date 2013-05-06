package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.GraphPoint;
import com.github.sunlong.hellomonitor.monitor.service.GraphService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-6
 * Time: 上午11:51
 */
@Controller
@RequestMapping("/graph")
public class GraphController {
    @Resource
    private GraphService graphService;

    @RequestMapping("/listGraphPoints")
    @ResponseBody
    public Result listGraphPoints(Integer id) throws AppException {
        return new Result(true, graphService.find(id).getGraphPoints());
    }

    @RequestMapping(value = "/addGraphPoint")
    @ResponseBody
    public Result addGraphPoint(GraphPoint graphPoint) throws AppException {
        graphService.createGraphPoint(graphPoint);
        return new Result();
    }
}
