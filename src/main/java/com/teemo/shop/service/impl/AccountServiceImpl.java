package com.teemo.shop.service.impl;

import com.teemo.shop.dao.AccountDao;
import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("accountService")
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
    @Resource
    AccountDao accountDao;

    @Override
    public Result register(Account account) {
        Account hasDb=accountDao.findAccountByUsername(account.getUsername());
        if(hasDb!=null){
            return Result.fail().message("名称已被注册");
        }
        accountDao.save(account);
        return Result.success().message("注册成功");
    }
}
