package com.chenfj.service;

import com.chenfj.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/4 10:51
 * @Description:
 * @version: 1.0
 */
public interface UserService {
    User selectUserById(int id);
    PageInfo<User> selectUserByPage(int pageSize, int pageNum);
    void updateUser(User user);

    User selectUserByUsername(String username);

    int insert(User user);
}
