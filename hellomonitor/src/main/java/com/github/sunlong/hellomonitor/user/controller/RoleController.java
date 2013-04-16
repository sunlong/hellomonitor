package com.github.sunlong.hellomonitor.user.controller;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.MessageUtil;
import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.model.Permission;
import com.github.sunlong.hellomonitor.user.model.Role;
import com.github.sunlong.hellomonitor.user.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-2-2
 * Time: 上午11:20
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Model model){
        Page<Role> roles = roleService.list(pageNumber, pageSize);
        model.addAttribute("roles", roles);
        return "user/role/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create1() throws AppException {
        return "user/role/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create2(Role role) throws AppException {
        roleService.save(role);
        return new Result();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update1(Integer id, Model model) throws AppException {
        model.addAttribute("role", roleService.find(id));
        return "user/role/create";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update2(Role role) throws AppException {
        roleService.update(role);
        return new Result();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        roleService.delete(new Integer[]{id});
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
            roleService.delete(toDeleteIdArray);
            return new Result();
        }else{
            return new Result(false, MessageUtil.getMessage(MessageCode.ROLE_ID_NOT_EXIST_ERROR));
        }
    }

    @RequestMapping(value = "/updatePermission")
    @ResponseBody
    public Result updatePermission(Integer id, String permission) throws AppException {
        List<Permission> permissionList = new ArrayList<Permission>();
        Role role = new Role(id, permissionList);

        String[] strPermissions = permission.split(",");
        for(String strPermission : strPermissions){
            if(StringUtils.isNotBlank(strPermission)){
                permissionList.add(new Permission(role, strPermission));
            }
        }

        roleService.updatePermission(role);
        return new Result();
    }

    @RequestMapping(value = "/listPermissions")
    @ResponseBody
    public Result listPermissions(Integer id) throws AppException {
        return new Result(true, roleService.find(id).getPermissions());
    }
}
