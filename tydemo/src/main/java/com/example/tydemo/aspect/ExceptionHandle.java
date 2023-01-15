package com.example.tydemo.aspect;

import com.example.tydemo.core.RUtil;
import com.example.tydemo.exception.REnum;
import com.example.tydemo.exception.RException;
import com.example.tydemo.vo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//统一异常处理
@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    public R handel(Exception e) {
        if(e instanceof RException) {
            RException rException =(RException) e;
            return RUtil.error(rException.getCode(), rException.getMessage());
        }
        return RUtil.error(REnum.UNKOWN_ERR);
    }
}
