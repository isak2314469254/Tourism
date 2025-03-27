package com.trytry.lasttry.pojo;

import lombok.Data;

@Data
public class Result {
    private Integer code; //编码：200成功，401为失败
    private String message; //错误信息
    private Object data; //数据

    public static Result success() {
        Result result = new Result();
        result.code = 200;
        result.message = "success";
        return result;
    }

    public static Result success(Object object, String msg) {
        Result result = new Result();
        result.data = object;
        result.code = 200;
        result.message = msg;
        return result;
    }

    public static Result success(Object object, Integer code, String msg) {
        Result result = new Result();
        result.data = object;
        result.code = code;
        result.message = msg;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.message = msg;
        result.code = 401;
        return result;
    }

}
