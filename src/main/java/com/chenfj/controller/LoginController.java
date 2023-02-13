package com.chenfj.controller;

import com.chenfj.config.properties.JdbcProperties;
import com.chenfj.model.User;
import com.chenfj.response.ResponseResult;
import com.chenfj.security.auth.JwtTokenProvider;
import com.chenfj.security.auth.LoginUserVo;
import com.chenfj.service.LoginService;
import com.chenfj.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/28 14:57
 * @Description:
 * @version: 1.0
 */
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JdbcProperties jdbcProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LoginService loginService;



    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        String encode = passwordEncoder.encode("123456");
        logger.info(encode);

        ResponseResult responseResult = loginService.login(user);


        return responseResult;
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        loginService.logout();

        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg("推出成功");
        responseResult.setSuccess(true);

        return responseResult;

    }

    @GetMapping("/getToken")
    public ResponseResult getToken(){
        String userId = UUID.randomUUID().toString();
        //String token = jwtTokenProvider.createToken(new LoginUserVo());


        return ResponseResult.returnSuccess("ceshi");
    }

    @GetMapping("/getUsername")
    public ResponseResult getUsernameFromToken(){
        //JSONObject jsonObject = jwtTokenProvider.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcIjk1NjUwZGZhLThkY2EtNGMzYS05NjQyLTE3NTY2MmI4YmYwN1wiLFwidXNlcm5hbWVcIjpcImNoZW5malwifSIsImV4cCI6MTY3MjIyNDg4MX0.-1KNbfvenGBQy0dK1g3x0rUXyaxKigoJSf73Mjoy1BgtSZUhBeBKb9Nq3vXF_BIf0HUUZKzewDTqg1INyJuKGw");
        //Object username = jsonObject.get("username");

        return ResponseResult.returnSuccess("username");
    }



}
