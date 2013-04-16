package com.github.sunlong.hellomonitor.exception;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.MessageUtil;
import com.github.sunlong.hellomonitor.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-1-6
 * Time: 下午5:31
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        String msg;
        if(e instanceof AppException){
            AppException appException = (AppException)e;
            msg = MessageUtil.getMessage(appException.getMsgCode(), appException.getArgs());
        }else if(e instanceof DataIntegrityViolationException){
            msg = MessageUtil.getMessage(MessageCode.DATA_INTEGRITY_VIOLATION_EXCEPTION_ERROR);
        }else{
            msg = e.getMessage();
        }
        if(isAjax){
            try {
                HttpMessageConverter<?> a = new MappingJacksonHttpMessageConverter();
                ((HttpMessageConverter<Result>)a).write(new Result(false, msg), new MediaType("application","json", Charset.forName("UTF-8")), new ServletServerHttpResponse(response));
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
            return null;
        }

        Map<String, String> model = new HashMap<String, String>();
        model.put("data", msg);
        if(request.getMethod().equals("POST")){//如果是post请求，提交表单
            String referer = request.getHeader("referer");

            int end = referer.indexOf("?");
            if(end != -1){
                return new ModelAndView("redirect:"+referer.substring(referer.indexOf("/", 8), end), model);
            }else{
                return new ModelAndView("redirect:"+referer.substring(referer.indexOf("/", 8)), model);
            }
        }else{
            return new ModelAndView("error/500", model);
        }
    }
}
