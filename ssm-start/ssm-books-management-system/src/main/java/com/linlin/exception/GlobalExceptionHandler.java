package com.linlin.exception;

import com.linlin.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理参数校验异常
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBindException(BindException e) {
        // 获取第一个错误信息
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return new R(400, false, errorMessage);
    }

    // 处理其他异常
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public R handleException(Exception e) {
//        // 记录异常信息
//        e.printStackTrace();
//        // 返回统一的错误响应
//        return R.fail("服务器内部错误");
//    }
}
