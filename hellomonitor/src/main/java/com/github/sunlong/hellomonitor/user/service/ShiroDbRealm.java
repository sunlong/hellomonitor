package com.github.sunlong.hellomonitor.user.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.MessageUtil;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.model.User;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午5:09
 */
public class ShiroDbRealm extends AuthorizingRealm {
    @Resource
    protected UserService userService;
    private Logger logger = LoggerFactory.getLogger("shrio");

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        try {
            User user = userService.findUserByUsername(token.getUsername());
            byte[] salt = Hex.decodeHex(user.getSalt().toCharArray());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new User(user.getId(), user.getUsername(), user.getEmail()), user.getPassword(), ByteSource.Util.bytes(salt), getName());
            //更新最后登录时间
            user.setLastLoginDate(new Date());
            userService.update(user);
            return info;
        } catch (AppException e) {
            throw new UnknownAccountException(e);
        } catch (DecoderException e) {
            throw new UnknownAccountException(e);
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User shiroUser = (User) principals.getPrimaryPrincipal();
        User user = null;
        try {
            user = userService.findUserByUsername(shiroUser.getUsername());
        } catch (AppException e) {
            logger.error(MessageUtil.getMessage(MessageCode.USER_GET_AUTHENTICATION_INFO_ERROR), shiroUser.getUsername(), e.getMessage());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(user.getRole().getName());
        info.addStringPermissions(user.getRole().getStringPermissions());
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
        matcher.setHashIterations(UserService.HASH_ITERATIONS);

        setCredentialsMatcher(matcher);
    }
}
