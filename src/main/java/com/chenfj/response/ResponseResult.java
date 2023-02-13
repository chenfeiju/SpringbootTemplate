package com.chenfj.response;

import java.io.Serializable;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/28 15:07
 * @Description:
 * @version: 1.0
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -6323673198661973297L;

    private String msg;
    private T data;
    private boolean success;
    private String code;

    public static ResponseResult returnSuccess(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(true);
        return responseResult;
    }
    public static <T> ResponseResult<T> returnSuccess(T t){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(true);
        responseResult.setData(t);
        return responseResult;
    }

    public static ResponseResult returnFail(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        return responseResult;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
