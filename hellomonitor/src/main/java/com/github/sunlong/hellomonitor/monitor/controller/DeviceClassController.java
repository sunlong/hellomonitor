package com.github.sunlong.hellomonitor.monitor.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.TreeNode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.model.DeviceClass;
import com.github.sunlong.hellomonitor.monitor.service.DeviceClassService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-4-24
 * Time: 上午9:22
 */
@Controller
@RequestMapping(value = "/deviceClass")
public class DeviceClassController {
    @Resource
    private DeviceClassService deviceClassService;

    @RequestMapping(value = "/list")
    public String list(){
        return "monitor/deviceclass/list";
    }

    @RequestMapping(value = "/listSub")
    @ResponseBody
    public List<TreeNode> listSubUserGroup(Integer parentId){
        List<DeviceClass> deviceClasses = deviceClassService.list(parentId);
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for(DeviceClass deviceClass : deviceClasses){
            nodes.add(new TreeNode(deviceClass.getId().toString(), deviceClass.getName(), deviceClassService.hasChildren(deviceClass.getId())));
        }
        return nodes;
    }

    @RequestMapping(value = "/create")
    @ResponseBody
    public Result create(DeviceClass deviceClass) throws AppException {
        deviceClassService.create(deviceClass);
        return new Result();
    }
}
