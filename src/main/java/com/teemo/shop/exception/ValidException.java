package com.teemo.shop.exception;

import com.teemo.shop.domain.bean.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.BindingResult;


/**
 *  参数验证异常
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ValidException extends  BaseException {
    private BindingResult bindingResult;
    public ValidException(BindingResult bindingResult) {
        super(ResultEnum.PARAM_VALID_ERROR);
        this.bindingResult=bindingResult;
    }
}
