package com.chenfj.service.impl;

import com.chenfj.mapper.UserMapper;
import com.chenfj.model.User;
import com.chenfj.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/4 10:52
 * @Description:
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User selectUserById(int id) {
        User user = new User();
        user.setId(id);
        User userModel = userMapper.selectUserById(user);
        return userModel;
    }

    @Override
    public PageInfo<User> selectUserByPage(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);

        List<User> list = userMapper.selectUserByPage();

        return new PageInfo<User>(list);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUserName(user);
    }

    @Override
    public User selectUserByUsername(String username) {
        User user = new User();
        user.setUserName(username);
        User userM = userMapper.selectUserByUsername(user);
        return userM;
    }

    @Override
    public int insert(User user) {
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        return 0;
    }
}
