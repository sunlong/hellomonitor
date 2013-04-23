package com.github.sunlong.hellomonitor.user.controller;

import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.TreeNode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.model.UserGroup;
import com.github.sunlong.hellomonitor.user.service.UserGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-2-5
 * Time: 上午9:01
 */
@Controller
@RequestMapping(value = "/userGroup")
public class UserGroupController {
    @Resource
    private UserGroupService userGroupService;

    @RequestMapping(value = "/list")
    public String list(){
        return "user/userGroup/list";
    }

    @RequestMapping(value = "/listSub")
    @ResponseBody
    public List<TreeNode> listSubUserGroup(Integer parentUserGroupId){
        List<UserGroup> groups = userGroupService.list(parentUserGroupId);
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for(UserGroup userGroup : groups){
            nodes.add(new TreeNode(userGroup, userGroupService.hasChildren(userGroup.getId())));
        }

        return nodes;
    }

    @RequestMapping(value = "/create")
    @ResponseBody
    public Result create(UserGroup userGroup) throws AppException {
        userGroupService.create(userGroup);
        return new Result();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result update(UserGroup userGroup) throws AppException {
        userGroupService.update(userGroup);
        return new Result();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        userGroupService.delete(id);
        return new Result();
    }

    @ResponseBody
    @RequestMapping(value = "/changeParent")
    public Result changeParent(Integer id, Integer parentId) throws AppException {
        userGroupService.updateParent(id, parentId);
        return new Result();
    }
}
