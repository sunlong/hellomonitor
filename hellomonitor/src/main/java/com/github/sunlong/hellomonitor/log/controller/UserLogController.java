package com.github.sunlong.hellomonitor.log.controller;

import com.github.sunlong.hellomonitor.common.SearchBean;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.log.model.UserLog;
import com.github.sunlong.hellomonitor.log.service.UserLogService;
import com.github.sunlong.hellomonitor.user.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午5:40
 */
@Controller
@RequestMapping(value = "/log")
public class UserLogController {
    @Resource
    private UserLogService userLogService;

    @RequestMapping(value = "/list")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            SearchBean searchBean,
            SortBean sortBean,
            Model model){
        Map<String, Object> params = new HashMap<String, Object>();
        if(searchBean.getParams() != null){
            params.put("username", searchBean.getParams().get("username"));
            params.put("ip", searchBean.getParams().get("ip"));
            params.put("message", searchBean.getParams().get("message"));
        }

        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        if(!subject.hasRole("管理员")){//不是管理员
            params.put("username", currentUser.getUsername());
            params.put("like", false);
        }

    	Page<UserLog> logs = userLogService.list(page, pageSize, params, sortBean);
        model.addAttribute("logs", logs);
        model.addAttribute("searchParams", searchBean.genSearchParams());
        model.addAttribute("params", searchBean.getParams());
        model.addAttribute("sortBean", sortBean);
        return "log/list";
    }
}