package com.teemo.shop.exception;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.bean.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/***
 *  常见的业务逻辑异常
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BusinessException extends BaseException {
    private String detail;
    public BusinessException(String detail){
        super(ResultEnum.FAIL);
        this.detail=detail;
    }
}
