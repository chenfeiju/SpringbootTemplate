package com.chenfj.security.auth;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 20:22
 * @Description:
 * @version: 1.0
 */
public class LoginUserVo {
    private String userName;
    private long loginTime;
    private long expireTime;
    private String token;// 即userId

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
