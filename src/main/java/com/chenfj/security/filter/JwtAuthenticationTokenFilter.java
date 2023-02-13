package com.chenfj.security.filter;


/**
 * JWT自定义认证过滤器
 * @Auther: feiju.chen
 * @Date: 2022/12/29 14:10
 * @Description:
 * @version: 1.0
 */

import com.chenfj.security.auth.JwtTokenProvider;
import com.chenfj.security.auth.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtTokenProvider tokenManager;


    public JwtAuthenticationTokenFilter(JwtTokenProvider tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, IOException {
        // 获取token
        // 解析token
        // 从redis 获取用户信息
        LoginUserVo loginUser = tokenManager.getLoginUser(request);
        if(Objects.isNull(loginUser)){
            chain.doFilter(request,response);
            return;
        }
        // 若过期自动刷新
        tokenManager.verifyToken(loginUser);
        // 存入securityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        chain.doFilter(request,response);
    }
}

