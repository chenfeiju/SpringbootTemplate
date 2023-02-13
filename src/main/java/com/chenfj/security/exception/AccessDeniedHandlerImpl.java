package com.chenfj.security.exception;

import com.alibaba.fastjson.JSON;
import com.chenfj.response.ResponseResult;
import com.chenfj.util.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/30 15:58
 * @Description:
 * @version: 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMsg("您的权限不足11");
        responseResult.setCode(HttpStatus.UNAUTHORIZED.value()+"");
        String jsonString = JSON.toJSONString(responseResult);
        WebUtil.renderString(response,jsonString);
    }
}
