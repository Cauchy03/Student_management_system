package com.example.tydemo.exception;
//关于异常的枚举类
public enum REnum {
    UNKOWN_ERR(-999,"未知错误"),
    COMMON_ERR(-10,"一般性错误"),
    LOGIN_ERR(-2,"出错，不正确的用户名和密码"),
    SUCCESS(1,"成功");

    private Integer code;
    private String msg;

    REnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
