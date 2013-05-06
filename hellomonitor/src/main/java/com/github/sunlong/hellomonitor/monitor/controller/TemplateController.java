package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.SearchBean;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.*;
import com.github.sunlong.hellomonitor.monitor.service.TemplateService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-4-25
 * Time: 上午10:04
 */
@Controller
@RequestMapping("/template")
public class TemplateController {
    @Resource
    private TemplateService templateService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int pageSize,
                       SearchBean searchBean,
                       SortBean sortBean,
                       Model model){
        Map<String, Object> params = new HashMap<String, Object>();
        if(searchBean.getParams() != null){
            params.put("name", searchBean.getParams().get("name"));
        }
        Page<Template> templates = templateService.list(page, pageSize, params, sortBean);
        model.addAttribute("templates", templates);
        model.addAttribute("searchParams", searchBean.genSearchParams());
        model.addAttribute("params", searchBean.getParams());
        model.addAttribute("sortBean", sortBean);
        return "monitor/template/list";
    }

    @RequestMapping("/listDataSources")
    public String listDataSources(Integer id, Model model) throws AppException {
        model.addAttribute("template", templateService.find(id));
        return "monitor/template/listDataSources";
    }

    @RequestMapping("/listGraphs")
    public String listGraphs(Integer id, Model model) throws AppException {
        model.addAttribute("template", templateService.find(id));
        return "monitor/template/listGraphs";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create1(){
        return "monitor/template/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create2(Template template) throws AppException {
        templateService.create(template);
        return new Result();
    }

    @RequestMapping(value = "/addSnmpDataSource")
    @ResponseBody
    public Result addSnmpDataSource(SnmpDataSource dataSource) throws AppException {
        templateService.createDataSource(dataSource);
        return new Result();
    }

    @RequestMapping(value = "/addWmiDataSource")
    @ResponseBody
    public Result addWmiDataSource(WmiDataSource dataSource) throws AppException {
        templateService.createDataSource(dataSource);
        return new Result();
    }

    @RequestMapping(value = "/addCmdDataSource")
    @ResponseBody
    public Result addCmdDataSource(CommandDataSource dataSource) throws AppException {
        templateService.createDataSource(dataSource);
        return new Result();
    }
}
