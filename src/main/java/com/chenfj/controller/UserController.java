package com.chenfj.controller;

import com.chenfj.model.User;
import com.chenfj.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/2 13:26
 * @Description:
 * @version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //@PreAuthorize("hasAuthority('test')")
    @RequestMapping("/getUser/{id}")
    public Object getUser(@PathVariable int id) {
        User user = userService.selectUserById(id);
        return user;
    }

    @RequestMapping("/getUserByPage")
    public Object getUserByPage(int pageSize, int pageNum){

        PageInfo<User> pageInfo = userService.selectUserByPage(pageSize,pageNum);
        return pageInfo;
    }

    @RequestMapping("/updateUserName/{id}")
    public Object updateUserName(User user){

        userService.updateUser(user);

        return "suc";

    }
}
