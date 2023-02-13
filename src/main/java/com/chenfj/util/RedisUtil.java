package com.chenfj.util;

import com.chenfj.security.auth.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/29 13:09
 * @Description:
 * @version: 1.0
 */
@Component
public class RedisUtil {

    @Autowired
    private ValueOperations<String, Object> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;

    public void set(String key,String value){
        valueOperations.set(key,value);
    }

    public String get(String key) {
        Object o = valueOperations.get(key);
        return (String) o;

    }

    public void setCacheObject(String userKey, LoginUserVo loginUser, long expiration, TimeUnit minutes) {
        valueOperations.set(userKey,loginUser,expiration,minutes);
    }

    public LoginUserVo getCacheObject(String userKey) {
        LoginUserVo loginUserVo = (LoginUserVo) valueOperations.get(userKey);;
        return loginUserVo;
    }

    public void deleteObject(String userKey) {
        valueOperations.getAndDelete(userKey);
    }
}
