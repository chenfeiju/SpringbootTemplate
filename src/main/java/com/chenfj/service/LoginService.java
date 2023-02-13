package com.chenfj.service;

import com.chenfj.model.User;
import com.chenfj.response.ResponseResult;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 17:02
 * @Description:
 * @version: 1.0
 */
public interface LoginService {
    ResponseResult login(User user);

    void logout();
}
