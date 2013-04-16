package com.github.sunlong.hellomonitor.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-3-6
 * Time: 上午9:03
 */
public class JsonParser {
    private static Logger logger = LoggerFactory.getLogger(JsonParser.class);

    /** ObjectMapper */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换response
     * @param json  String
     * @param clazz Class
     * @return IBaseResponse
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static BaseResponse convertResponse(String json, Class clazz) {
        BaseResponse response = null;
        if(json != null){
            try {
                response = (BaseResponse)objectMapper.readValue(json, clazz);
            } catch (JsonParseException ex) {
                logger.error(ex.getMessage(), ex);
            } catch (JsonMappingException ex) {
                logger.error(ex.getMessage(), ex);
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }else{
            try {
                response = (BaseResponse)clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
