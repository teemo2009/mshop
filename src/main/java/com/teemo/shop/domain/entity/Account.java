package com.teemo.shop.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class Account {
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "用户名不能为空")
    private String username;
}
