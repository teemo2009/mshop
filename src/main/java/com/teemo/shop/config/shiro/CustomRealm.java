package com.teemo.shop.config.shiro;

import com.teemo.shop.domain.dto.AccountDTO;
import com.teemo.shop.util.JWTUtil;
import com.teemo.shop.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

@Slf4j
public class CustomRealm extends  BaseCustomRealm {

    @Resource
    JWTUtil jwtUtil;

    @Autowired
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
           //判断token是否一致
           AccountDTO accountDTO=redisUtil.get(key);
           if(!accountDTO.getToken().equals(clientDigest)){
               log.info("异地登录");
               throw new DisabledAccountException();
           }
           return new SimpleAuthenticationInfo(accountDTO, clientDigest, this.getName());
       }else{
           //没有登录
           return null;
       }

    }
}
