package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.SearchBean;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import com.github.sunlong.hellomonitor.monitor.service.DeviceService;
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
 * Date: 13-4-23
 * Time: 下午3:30
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
    @Resource
    private DeviceService deviceService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int pageSize,
                       SearchBean searchBean,
                       SortBean sortBean,
                       Model model){
        Map<String, Object> params = new HashMap<String, Object>();
        if(searchBean.getParams() != null){
            params.put("ip", searchBean.getParams().get("ip"));
            params.put("name", searchBean.getParams().get("name"));
        }

        Page<Device> devices = deviceService.list(page, pageSize, params, sortBean);
        model.addAttribute("devices", devices);
        model.addAttribute("searchParams", searchBean.genSearchParams());
        model.addAttribute("params", searchBean.getParams());
        model.addAttribute("sortBean", sortBean);
        return "monitor/device/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create1() throws AppException {
        return "monitor/device/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create2(Device device) throws AppException {
        deviceService.create(device);
        return new Result();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update1(Integer id, Model model) throws AppException {
        model.addAttribute("device", deviceService.find(id));
        return "monitor/device/create";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update2(Device device) throws AppException {
        deviceService.update(device);
        return new Result();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        deviceService.delete(id);
        return new Result();
    }
}
