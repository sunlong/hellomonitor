package com.github.sunlong.hellomonitor.exception;

import com.github.sunlong.hellomonitor.common.IMsgCode;

/**
 * User: sunlong
 * Date: 12-12-28
 * Time: 下午9:10
 */
public class AppException extends Exception{
    private Object[] args;
    private IMsgCode msgCode;

    public AppException(){

    }

    public AppException(IMsgCode msgCode, Object... args){
        this.args = args;
        this.msgCode = msgCode;
    }

    public AppException(String msg, Throwable e){
        super(msg, e);
    }

    public AppException(Throwable e){
        super(e);
    }

    public Object[] getArgs() {
        return args;
    }

    public IMsgCode getMsgCode() {
        return msgCode;
    }
}