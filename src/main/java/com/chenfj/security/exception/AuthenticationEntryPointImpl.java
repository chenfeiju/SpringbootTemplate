package com.chenfj.security.exception;

import com.alibaba.fastjson.JSON;
import com.chenfj.response.ResponseResult;
import com.chenfj.util.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/30 15:46
 * @Description:
 * @version: 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint{


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMsg("认证查询失败");
        responseResult.setCode(HttpStatus.UNAUTHORIZED.value()+"");
        String jsonString = JSON.toJSONString(responseResult);
        WebUtil.renderString(response,jsonString);
    }
}
