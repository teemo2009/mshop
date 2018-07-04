package com.teemo.shop.config.shiro;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShiroConfig extends BaseShiroConfig {

    @Override
    public BaseCustomRealm customRealm() {
        return new CustomRealm();
    }

    @Override
    public List<String> filterByAnon() {
        List<String> list=new ArrayList<>();
        list.add("/account/**");
        return list;
    }
}
