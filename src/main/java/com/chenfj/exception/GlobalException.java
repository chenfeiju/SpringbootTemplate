package com.chenfj.exception;

import com.chenfj.response.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: feiju.chen
 * @Date: 2023/1/4 16:08
 * @Description:
 * @version: 1.0
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handleException(Exception e){

        ResponseResult responseResult = ResponseResult.returnFail();
        responseResult.setCode(500+"");
        responseResult.setMsg(e.getMessage());
        return responseResult;
    }
}
