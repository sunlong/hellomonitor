package com.github.sunlong.hellomonitor.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;

/**
 * User: sunlong
 * Date: 13-2-28
 * Time: 上午11:40
 */
public class MessageUtil {
    public static String getMessage(String value){
        return getMessage(value, null, null);
    }

    public static String getMessage(String value, Object[] args){
        return getMessage(value, args, null);
    }

    public static String getMessage(String value, Locale locale){
        return getMessage(value, null, locale);
    }

    public static String getMessage(IMsgCode value){
        return getMessage(value.toString(), null, null);
    }

    public static String getMessage(IMsgCode value, Object[] args){
        return getMessage(value.toString(), args, null);
    }

    public static String getMessage(IMsgCode value, Locale locale){
        return getMessage(value.toString(), null, locale);
    }

    public static String getMessage(String value, Object[] args, Locale locale){
        if(locale == null){
            locale = Locale.getDefault();
        }
        String key = value.toLowerCase().replaceAll("_", ".");
        return MessageFormat.format(PropertyResourceBundle.getBundle("resources/messages", locale).getString(key), args);
    }
}
