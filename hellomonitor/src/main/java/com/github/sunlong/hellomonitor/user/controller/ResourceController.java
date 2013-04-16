package com.github.sunlong.hellomonitor.user.controller;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.MessageUtil;
import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.TreeNode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.model.Action;
import com.github.sunlong.hellomonitor.user.model.Resource;
import com.github.sunlong.hellomonitor.user.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-2-18
 * Time: 上午10:17
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController {
    @javax.annotation.Resource
    private ResourceService resourceService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create1() throws AppException {
        return "user/resource/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create2(Resource resource) throws AppException {
        resourceService.save(resource);
        return new Result();
    }

    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,String name, Model model){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("name", name);
    	Page<Resource> resources = resourceService.list(pageNumber, pageSize, params);
        model.addAttribute("resources", resources);
        return "user/resource/list";
    }

    @RequestMapping(value = "/listTree")
    @ResponseBody
    public List<TreeNode> listTree(Integer resourceId) throws AppException {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        if(resourceId == null){//resource
            List<Resource> resources = resourceService.listAll();
            for(Resource resource: resources){
                nodes.add(new TreeNode(resource));
            }
        }else{//resource action
            List<Action> actions = resourceService.find(resourceId).getActions();
            for(Action action: actions){
                nodes.add(new TreeNode(action));
            }
        }
        return nodes;
    }

    @RequestMapping(value = "/listActions")
    @ResponseBody
    public Result listActions(Integer id) throws AppException {
        return new Result(true, resourceService.find(id).getActions());
    }

    @RequestMapping(value = "/addAction")
    @ResponseBody
    public Result addAction(Action action) throws AppException {
        resourceService.addAction(action);
        //初始化action的id
        List<Action> actions = resourceService.find(action.getResource().getId()).getActions();
        for(Action tmp: actions){
            if(tmp.getName().equals(action.getName())){
                action.setId(tmp.getId());
            }
        }//~
        return new Result(true, action);
    }

    @RequestMapping(value = "/deleteAction")
    @ResponseBody
    public Result deleteAction(Integer actionId) throws AppException {
        resourceService.deleteAction(actionId);
        return new Result();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update1(Integer id, Model model) throws AppException {
        model.addAttribute("resource", resourceService.find(id));
        return "user/resource/create";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update2(Resource resource) throws AppException {
        resourceService.update(resource);
        return new Result();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        resourceService.delete(new Integer[]{id});
        return new Result();
    }

    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public Result batchDelete(String ids) throws AppException {
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            Integer[] toDeleteIdArray = new Integer[idArray.length];
            for(int i=0; i<idArray.length; i++){
                toDeleteIdArray[i] = Integer.valueOf(idArray[i]);
            }
            resourceService.delete(toDeleteIdArray);
            return new Result();
        }else{
            return new Result(false, MessageUtil.getMessage(MessageCode.RESOURCE_ID_NOT_EXIST_ERROR));
        }
    }
}
