package com.chenfj.mapper;

import com.chenfj.model.User;

import java.util.List;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/4 10:42
 * @Description:
 * @version: 1.0
 */
public interface UserMapper {
    User selectUserById(User user);

    List<User> selectUserByPage();

    void updateUserName(User user);

    User selectUserByUsername(User user);
}
