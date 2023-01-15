package com.example.tydemo.vo;

import lombok.Data;

@Data
public class R<T> {
    //代码
    private Integer code;
    //提示信息
    private String msg;
    //内容
    private T data;
}
