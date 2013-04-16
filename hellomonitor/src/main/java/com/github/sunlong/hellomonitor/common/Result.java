package com.github.sunlong.hellomonitor.common;

/**
 * 如果success true，data返回的是实际的数据
 * 如果false，则data返回的是错误消息
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午3:18
 */
public class Result {
    private boolean success;
    private Object data;

    public Result(){
        this.success = true;
    }

    public Result(boolean success, Object data){
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}
