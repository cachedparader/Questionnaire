package com.qqcn.common.exception;

import com.qqcn.common.constant.ResultConstant;
import com.qqcn.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<?> handleExcetion(Exception e){
        e.printStackTrace();
        log.error("系统异常：" + e.getMessage());
        return  Result.fail(ResultConstant.FAIL_SYSTEM_EXCEPTION.getCode(),ResultConstant.FAIL_SYSTEM_EXCEPTION.getMessage());
    }
}
