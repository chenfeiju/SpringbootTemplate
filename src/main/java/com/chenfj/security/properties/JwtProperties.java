package com.chenfj.security.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: feiju.chen
 * @Date: 2022/12/28 15:53
 * @Description:
 * @version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @Value("${jwt.secret}")
    private String appSecret;

    @Value("${jwt.expiration}")
    private long expiration;



    @Value("${jwt.tokenHeader}")
    private String tokenHeader;




    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }


    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }
}