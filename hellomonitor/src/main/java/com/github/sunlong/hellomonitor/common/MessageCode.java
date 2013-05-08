package com.github.sunlong.hellomonitor.common;

/**
 * User: sunlong
 * Date: 13-2-1
 * Time: 上午9:56
 */
public enum MessageCode implements IMsgCode{
    //user
    USER_GET_AUTHENTICATION_INFO_ERROR,
    USER_EXIST_ERROR,
    USER_NOT_EXIST_ERROR,
    USER_NAME_EXIST_ERROR,
    USER_NEW_PASSWORD_BLANK_ERROR,
    USER_TWICE_PASSWORD_NOT_EQUAL_ERROR,
    USER_OLD_PASSWORD_ERROR,
    USER_PASSWORD_BLANK_ERROR,
    USER_DELETE_DEFAULT_ERROR,

    USER_GROUP_EXIST_ERROR,
    USER_GROUP_NOT_EXIST_ERROR,
    USER_GROUP_PARENT_NOT_EXIST_ERROR,
    USER_GROUP_DELETE_DEFAULT_ERROR,
    USER_GROUP_UPDATE_DEFAULT_NAME_ERROR,

    ROLE_EXIST_ERROR,
    ROLE_NOT_EXIST_ERROR,
    ROLE_ID_NOT_EXIST_ERROR,
    ROLE_DELETE_DEFAULT_ERROR,
    ROLE_UPDATE_DEFAULT_ROLE_NAME_ERROR,

    RESOURCE_EXIST_ERROR,
    RESOURCE_NOT_EXIST_ERROR,
    RESOURCE_ID_NOT_EXIST_ERROR,

    ACTION_EXIST_ERROR,
    ACTION_NOT_EXIST_ERROR,

    //other
    HEX_DECODE_EXCEPTION_ERROR,
    DATA_INTEGRITY_VIOLATION_EXCEPTION_ERROR,
    VALIDATION_ERROR,

    DEVICE_CLASS_EXIST_ERROR,
    TEMPLATE_EXIST_ERROR,
    TEMPLATE_NOT_EXIST_ERROR,
    DATA_SOURCE_NOT_EXIST_ERROR,
    GRAPH_NOT_EXIST_ERROR, DEVICE_NOT_EXIST_ERROR, DEVICE_EXIST_ERROR, DEVICE_CLASS_NOT_EXIST_ERROR, DATA_SOURCE_EXIST_ERROR, GRAPH_EXIST_ERROR, GRAPH_POINT_NOT_EXIST_ERROR,
}