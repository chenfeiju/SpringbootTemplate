package com.chenfj.security;

import com.chenfj.model.User;
import com.chenfj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 15:40
 * @Description:
 * @version: 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userService.selectUserByUsername(username);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        //TODO 查询对应的权限信息
        List<String> permissions = new ArrayList<>();
        //permissions.add("test");

        return new LoginUser(user,permissions);
    }
}
