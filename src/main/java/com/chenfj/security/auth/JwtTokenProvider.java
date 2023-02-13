package com.chenfj.security.auth;

import com.chenfj.security.LoginUser;
import com.chenfj.security.constants.Constants;
import com.chenfj.security.properties.JwtProperties;
import com.chenfj.util.RedisUtil;
import com.github.pagehelper.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @className: TokenManager
 * @description: jwt令牌管理：
 * JWT实现大体思路：
 * 根据UUID生成一个JWT——token，此 token 并不会设置过期时间，只做数据的记录
 * （缓存前缀 + UUID） 作为 Redis 缓存对象的 key，同时 UUID 又是保存在 token 中的，所以 token 也就与 Redis关联了起来
 * 只有通过 token 获取到 UUID后，才能在 Redis 中获取到登录对象，如果没有 token，也就获取不到对象，则认证失败，否则成功
 * @author: LiJunYi
 * @create: 2022/7/27 11:16
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Autowired
    private JwtProperties jwtProperties;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private  RedisUtil redisCache;



    /**
     * 从 request 中获取token
     * 通过解析token获取用户在redis中缓存的key从而获取到登录用户信息
     *
     * @param request 请求
     * @return {@link LoginUser}
     */
    public LoginUserVo getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtil.isNotEmpty(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 获取UUID
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                // 获取 Redis 缓存中的 key
                String userKey = getTokenKey(uuid);
                return redisCache.getCacheObject(userKey);
            } catch (Exception ignored) {
                logger.info("error",ignored);
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUserVo loginUser)
    {
        if (Objects.isNull(loginUser) && StringUtil.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    public void removeToken(String token)
    {
        if (StringUtil.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 登录用户
     * @return {@link String}
     */
    public String createToken(LoginUserVo loginUser)
    {

        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>(8);
        claims.put(Constants.LOGIN_USER_KEY, loginUser.getToken());
        claims.put(Constants.LOGIN_USER_NAME_KEY,loginUser.getUserName());
        return createToken(claims);
    }


    /**
     * 验证令牌有效期，自动刷新缓存
     *
     * @param loginUser 登录用户
     */
    public void verifyToken(LoginUserVo loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 创建token时存入当前登录用户并同时刷新令牌过期时间
     *
     * @param loginUser 登录用户
     */
    public void refreshToken(LoginUserVo loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + jwtProperties.getExpiration() * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, jwtProperties.getExpiration(), TimeUnit.MINUTES);
    }

    /**
     * 创建令牌
     *
     * @param claims 数据声明
     * @return {@link String}
     */
    public String createToken(Map<String, Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getAppSecret()).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getAppSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取令牌
     *
     * @param request 请求
     * @return {@link String}
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(jwtProperties.getTokenHeader());
        if (StringUtil.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 获取令牌在缓存中的key
     *
     * @param uuid 随机UUID
     * @return {@link String}
     */
    private String getTokenKey(String uuid)
    {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
