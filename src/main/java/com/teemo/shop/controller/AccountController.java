package com.teemo.shop.controller;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.exception.ValidException;
import com.teemo.shop.service.AccountService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid Account account, BindingResult result){
        if(result.hasErrors()){
           throw new ValidException(result);
        }
        return Result.success().message("登录成功");
    }

    @PutMapping("/register")
    public Result register(@RequestBody @Valid Account account, BindingResult result){
        if(result.hasErrors()){
            throw new ValidException(result);
        }
        return accountService.register(account);
    }

}
