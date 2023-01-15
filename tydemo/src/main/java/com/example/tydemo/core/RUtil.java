package com.example.tydemo.core;

import com.example.tydemo.exception.REnum;
import com.example.tydemo.vo.R;

import javax.persistence.criteria.CriteriaBuilder;

//对R对象进行简化
public class RUtil {
    public static R success(Object object){
        R result = new R();
        result.setCode(1);
        result.setData(object);
        result.setMsg("成功执行！");
        return result;
    }
    public static R sucess() {
        return success(null);
    }
    public static R error(Integer code,String msg) {
        R result = new R();
        result.setCode(code);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }
    public static R error(REnum rEnum) {
        return error(rEnum.getCode(),rEnum.getMsg());
    }
}
