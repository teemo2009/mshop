package com.teemo.shop.exception;

import com.teemo.shop.domain.bean.ResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/***
 *  业务逻辑异常
 * */
@Data
@Accessors(chain = true)
public class BaseException extends RuntimeException {
    private Integer code;
    public BaseException(){}
    public BaseException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
}
