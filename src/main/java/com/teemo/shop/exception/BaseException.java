package com.teemo.shop.exception;

import com.teemo.shop.domain.bean.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/***
 *  异常基类
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class BaseException extends RuntimeException {
    private Integer code;
    public BaseException(){}
    public BaseException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
}
