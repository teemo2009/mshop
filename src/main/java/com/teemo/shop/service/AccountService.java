package com.teemo.shop.service;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;

public interface AccountService {
    Result register(Account account);
    Result login(Account account);
}
