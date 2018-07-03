package com.teemo.shop.domain.entity;

import com.teemo.shop.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Table
@Entity
@Data
@Accessors(chain = true)
public class Account extends BaseEntity {
    @Column
    @NotNull(message = "密码不能为空")
    private String password;

    @Column(unique = true)
    @NotNull(message = "用户名不能为空")
    private String username;
}
