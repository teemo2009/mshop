package com.teemo.shop.controller;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.exception.ValidException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping("/login")
    public Result login(@RequestBody @Valid Account account, BindingResult result){
        if(result.hasErrors()){
           throw new ValidException(result);
        }
        return Result.success().message("登录成功");
    }

}
