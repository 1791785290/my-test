package com.example.data.utils;

public class ResultUtil {
    //当正确时返回的值
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    //当错误时返回的值
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(201);
        result.setMsg(msg);
        return result;
    }
}
