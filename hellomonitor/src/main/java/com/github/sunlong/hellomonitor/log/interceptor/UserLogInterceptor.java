package com.github.sunlong.hellomonitor.log.interceptor;

import com.github.sunlong.hellomonitor.common.MessageUtil;
import com.github.sunlong.hellomonitor.log.model.UserLog;
import com.github.sunlong.hellomonitor.log.service.UserLogService;
import com.github.sunlong.hellomonitor.user.model.User;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * 操作日志拦截器
 * User: sunlong
 * Date: 13-2-28
 * Time: 上午9:24
 */
@Aspect
@Component
public class UserLogInterceptor {
    @Resource
    private UserLogService userLogService;
    private Logger logger = LoggerFactory.getLogger("[user log interceptor]");

    @AfterReturning("execution(* com.github.sunlong.hellomonitor.*.service.*.save*(..)) || " +
            "execution(* com.github.sunlong.hellomonitor.*.service.*.update*(..)) || " +
            "execution(* com.github.sunlong.hellomonitor.*.service.*.create*(..)) || " +
            "execution(* com.github.sunlong.hellomonitor.*.service.*.delete*(..))")
    public void logAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String signature = joinPoint.toShortString();

        int first = signature.indexOf("(")+1;
        String method = signature.substring(first, signature.indexOf("(", first));
        try{
        	logger.info("method.toLowerCase:" + method.toLowerCase());
        	String message = MessageUtil.getMessage(method.toLowerCase(), args);
        	String ip = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        	User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        	String username = currentUser.getUsername();
        	UserLog userLog = new UserLog(message,username , ip);
            userLogService.create(userLog);
        }catch(Exception e){
        	logger.error(e.getMessage());
        }      
    }
}
