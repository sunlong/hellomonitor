package com.github.sunlong.hellomonitor.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * User: sunlong
 * Date: 13-3-25
 * Time: 下午4:48
 */
public class HasMultiPermissionTag extends PermissionTag {

    @Override
    protected boolean showTagBody(String p) {
        String[] permissions;
        if(p.contains("&&")){
            permissions = p.split("&&");
            boolean permitted = true;
            for(String permission: permissions){
                if(StringUtils.isNotBlank(permission)){
                    permitted = (permitted && isPermitted(permission.trim()));
                }
            }
            return permitted;
        }else if(p.contains("||")){
            permissions = p.split("\\|\\|");
            boolean permitted = false;
            for(String permission: permissions){
                if(StringUtils.isNotBlank(permission)){
                    permitted = (permitted || isPermitted(permission.trim()));
                }
            }
            return permitted;
        }
        return isPermitted(p);
    }
}
