package com.teemo.shop.domain.dto;

import com.teemo.shop.domain.entity.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AccountDTO extends Account {
    private String token;
    private String refreshToken;
}
