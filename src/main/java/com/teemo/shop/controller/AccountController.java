package com.teemo.shop.controller;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.exception.ValidException;
import com.teemo.shop.mapper.AccountMapper;
import com.teemo.shop.service.AccountService;
import com.teemo.shop.util.MyThreadLocal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @Resource
    AccountMapper accountMapper;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid Account account, BindingResult result){
        if(result.hasErrors()){
           throw new ValidException(result);
        }
        return accountService.login(account);
    }

    @PutMapping("/register")
    public Result register(@RequestBody @Valid Account account, BindingResult result){
        if(result.hasErrors()){
            throw new ValidException(result);
        }
        return accountService.register(account);
    }


    @GetMapping("/all")
    public Result getAll(){
       List<Account> lists= accountMapper.selectAll();
       return Result.success().data(lists);
    }

}
