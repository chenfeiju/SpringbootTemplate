package com.chenfj.model;

import java.io.Serializable;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/4 10:52
 * @Description:
 * @version: 1.0
 */
public class User implements Serializable {

    private int id;
    private String userName;
    private String password;
    private String address;
    private int age;
    private int sex;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
