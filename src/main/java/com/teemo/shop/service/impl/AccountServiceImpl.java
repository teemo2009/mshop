package com.teemo.shop.service.impl;

import com.teemo.shop.dao.AccountDao;
import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.service.AccountService;
import com.teemo.shop.util.MUtil;
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
    @Resource
    MUtil mUtil;

    @Override
    public Result register(Account account) {
        Account hasDb=accountDao.findAccountByUsername(account.getUsername());
        if(hasDb!=null){
            return Result.fail().message("名称已被注册");
        }
        String md5Pwd=mUtil.MD5(account.getPassword());
        account.setPassword(md5Pwd);
        accountDao.save(account);
        return Result.success().message("注册成功");
    }
}
