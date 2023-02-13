package com.chenfj.handler;

import com.chenfj.security.auth.JwtTokenProvider;
import com.chenfj.security.auth.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/30 14:50
 * @Description:
 * @version: 1.0
 */
//@Configuration
    @Deprecated
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LoginUserVo loginUser = jwtTokenProvider.getLoginUser(request);
        if(!Objects.isNull(loginUser))
        {
            //移除
            jwtTokenProvider.removeToken(loginUser.getToken());

        }
        //ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
