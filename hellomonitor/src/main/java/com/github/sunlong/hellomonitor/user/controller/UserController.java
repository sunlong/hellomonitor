package com.github.sunlong.hellomonitor.user.controller;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.Result;
import com.github.sunlong.hellomonitor.common.SearchBean;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.model.Role;
import com.github.sunlong.hellomonitor.user.model.User;
import com.github.sunlong.hellomonitor.user.service.RoleService;
import com.github.sunlong.hellomonitor.user.service.UserGroupService;
import com.github.sunlong.hellomonitor.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午5:40
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserGroupService userGroupService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        return "user/user/login";
    }

    @RequestMapping(value = "/list")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            SearchBean searchBean,
            SortBean sortBean,
            Model model){
        Map<String, Object> params = new HashMap<String, Object>();
        if(searchBean.getParams() != null){
            params.put("username", searchBean.getParams().get("username"));
            params.put("email", searchBean.getParams().get("email"));
        }

        Page<User> users = userService.list(page, pageSize, params, sortBean);
        model.addAttribute("users", users);
        model.addAttribute("searchParams", searchBean.genSearchParams());
        model.addAttribute("params", searchBean.getParams());
        model.addAttribute("sortBean", sortBean);
        return "user/user/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update1(Integer id, Model model) throws AppException {
        User user = userService.find(id);
        List<Role> roles = roleService.listAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user/user/create";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update2(User user) throws AppException {
        userService.update(user);
        return new Result();
    }

    @RequestMapping(value = "/updatePersonal", method = RequestMethod.GET)
    public String updatePersonal1(Model model) throws AppException {
        User user = userService.find( ((User)SecurityUtils.getSubject().getPrincipal()).getId() );
        model.addAttribute("user", user);
        return "user/user/personal";
    }

    @RequestMapping(value = "/updatePersonal", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePersonal2(User user) throws AppException {
        userService.updatePersonal(user);
        return new Result();
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public String updatePassword1(Model model) throws AppException {
        model.addAttribute("user", SecurityUtils.getSubject().getPrincipal());
        return "user/user/password";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword2(User user, String newPassword, String rePassword) throws AppException {
        if(StringUtils.isBlank(newPassword)){
            throw new AppException(MessageCode.USER_NEW_PASSWORD_BLANK_ERROR);
        }
        if(!rePassword.equals(newPassword)){
            throw new AppException(MessageCode.USER_TWICE_PASSWORD_NOT_EQUAL_ERROR);
        }
        userService.updatePassword(user, newPassword);
        return new Result();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register1(){
        return "user/user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register2(User user, String rePassword) throws AppException {
        if(StringUtils.isBlank(user.getPassword())){
            throw new AppException(MessageCode.USER_PASSWORD_BLANK_ERROR);
        }
        if(!user.getPassword().equals(rePassword)){
            throw new AppException(MessageCode.USER_TWICE_PASSWORD_NOT_EQUAL_ERROR);
        }
        if(user.getUserGroup() == null){
            user.setUserGroup(userGroupService.findDefaultUserGroup());
        }
        if(user.getRole() == null){
            user.setRole(roleService.findDefaultRole());
        }
        userService.create(user);
        return new Result();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create1(Model model){
        List<Role> roles = roleService.listAll();
        model.addAttribute("roles", roles);
        return "user/user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create2(User user) throws AppException {
        userService.create(user);
        return new Result();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result delete(Integer id) throws AppException {
        userService.delete(id);
        return new Result();
    }

    @RequestMapping(value = "/center")
    public String center(Model model) throws AppException {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", userService.find(user.getId()));
        return "user/user/center";
    }
}