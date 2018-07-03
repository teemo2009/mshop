package com.teemo.shop.handle;

import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.exception.BaseException;
import com.teemo.shop.exception.ValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionHandle extends ResponseEntityExceptionHandler  {

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), status);
    }


    /**
     *  进入业务逻辑中出错
     * */
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e){
        if(e instanceof ValidException){
            ValidException validException= (ValidException) e;
            //验证参数错误
            return  Result.custom().code(validException.getCode())
                    .message(validException.getMessage())
                    .data(validException.getBindingResult().getFieldError().getDefaultMessage());
        } else if(e instanceof BaseException){
            BaseException baseException= (BaseException) e;
            //业务逻辑异常
            return  Result.fail().message(baseException.getMessage());
        }
        log.error("class:{},detail:{}",e.getClass().getName(),e.getMessage());
        //未知错误
        return Result.unkonw().data(e.getMessage());
    }

}
