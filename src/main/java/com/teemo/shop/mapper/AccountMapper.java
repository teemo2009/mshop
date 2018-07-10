package com.teemo.shop.mapper;

import com.teemo.shop.domain.entity.Account;

import java.util.List;

public interface AccountMapper {

    List<Account> selectAll();
}
