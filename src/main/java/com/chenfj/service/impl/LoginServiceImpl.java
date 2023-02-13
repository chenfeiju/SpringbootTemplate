package com.chenfj.service.impl;

import com.chenfj.model.User;
import com.chenfj.response.ResponseResult;
import com.chenfj.security.LoginUser;
import com.chenfj.security.auth.JwtTokenProvider;
import com.chenfj.security.auth.LoginUserVo;
import com.chenfj.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 17:02
 * @Description:
 * @version: 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenProvider jwtTokenProvider;



    @Override
    public ResponseResult login(User user) {

        // AuthenticationManager authenticate 进行用户认证
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(),
                user.getPassword());

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(token);
        }catch (Exception e){
            if (e instanceof BadCredentialsException) {
                throw new RuntimeException("用户名密码错误");
            }
            else {
                throw new RuntimeException(e.getMessage());
            }
        }

        // 如果认证没通过 给出响应的提示

        // 如果认证通过，使用userid 生成jwt token
        LoginUser principal = (LoginUser)authenticate.getPrincipal();
        int id = principal.getUser().getId();
        String userName = principal.getUser().getUserName();


        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setLoginTime(System.currentTimeMillis());
        loginUserVo.setUserName(userName);
        loginUserVo.setToken(id+"");


        String token1 = jwtTokenProvider.createToken(loginUserVo);

        // 把完整的用户信息存入redis token userId作为key

        Map<String,String> map = new HashMap<>();
        map.put("token",token1);
        ResponseResult<Map<String, String>> mapResponseResult = ResponseResult.returnSuccess(map);
        mapResponseResult.setMsg("登錄成功");
        return mapResponseResult;
    }

    @Override
    public void logout() {
        // 从 securityContextHolder 中获取token
        LoginUserVo principal = (LoginUserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String token = principal.getToken();
        // 从redis中
        jwtTokenProvider.removeToken(token);
    }


}
