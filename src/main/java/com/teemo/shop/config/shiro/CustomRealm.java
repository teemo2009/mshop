package com.teemo.shop.config.shiro;

import com.teemo.shop.domain.entity.Manager;
import com.teemo.shop.util.JWTUtil;
import com.teemo.shop.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

public class CustomRealm extends  BaseCustomRealm {

    @Resource
    JWTUtil jwtUtil;

    @Resource
    RedisUtil redisUtil;

    @Value("${redis.manager}")
    private String REDIS_MANAGER_KEY;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //权限授权
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //认证登录
       StatelessToken statelessToken= (StatelessToken) token;
       String username=statelessToken.getUsername();
       String clientDigest=statelessToken.getClientDigest();
       if(!jwtUtil.verify(username,clientDigest)){
           return null;
       }
       String key=REDIS_MANAGER_KEY+username;
       //判断缓存中是否有登陆信息
       if(redisUtil.hasKey(key)){
           Manager manager=redisUtil.get(key);
           return new SimpleAuthenticationInfo(manager, clientDigest, this.getName());
       }else{
           return null;
       }

    }
}
