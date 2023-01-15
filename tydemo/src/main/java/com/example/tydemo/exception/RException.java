package com.example.tydemo.exception;

import lombok.Data;

@Data
//自定义的运行异常类
public class RException extends RuntimeException{
    private Integer code;
    public RException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }
    public RException(REnum rEnum){
        super(rEnum.getMsg());
        this.code = rEnum.getCode();
    }
}
