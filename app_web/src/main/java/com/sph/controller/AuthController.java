package com.sph.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sph.bean.TokenResulet;
import com.sph.bean.WebResponse;
import com.sph.fegin.UserFegin;
import com.sph.fegin.bean.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class AuthController {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${token.expire.time}")
    private long tokenExpireTime;

    @Value("${refresh.token.expire.time}")
    private long refreshTokenExpireTime;

    @Value("${jwt.refresh.token.key.format}")
    private String jwtRefreshTokenKeyFormat;

    @Value("${jwt.blacklist.key.format}")
    private String jwtBlacklistKeyFormat;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserFegin userFegin;

    /**
     * 登录授权，生成JWT
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/auth")
    public WebResponse login(@RequestParam String userName,
                                    @RequestParam String password){
        WebResponse<User> webResponse = userFegin.queryUser(userName);
        if("10000".equals(webResponse.getCode())){
            if(null == webResponse.getData() || !password.equals(webResponse.getData().getPassword())){
                return WebResponse.create().fail("10004","登陆账号或者密码错误");
            }
            //生成JWT
            String token = buildJWT(userName);
            //生成refreshToken
            String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
            //保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
            String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
            stringRedisTemplate.opsForHash().put(refreshTokenKey,
                    "token", token);
            stringRedisTemplate.opsForHash().put(refreshTokenKey,
                    "userName", userName);
            //refreshToken设置过期时间
            stringRedisTemplate.expire(refreshTokenKey,
                    refreshTokenExpireTime, TimeUnit.MILLISECONDS);
            //返回结果
            TokenResulet tokenResulet = new TokenResulet();
            tokenResulet.setToken(token);
            tokenResulet.setRefreshToken(refreshToken);
            return WebResponse.create().success(tokenResulet);
        }else {
            return WebResponse.create().fail(webResponse.getCode(),webResponse.getDesc());
        }


    }

    /**
     * 注册账户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public WebResponse register(@RequestBody User user){
        WebResponse webResponse = userFegin.addUser(user);
        if("10000".equals(webResponse.getCode())){
            return WebResponse.create().success();
        }else {
            return WebResponse.create().fail(webResponse.getCode(),webResponse.getDesc());
        }
    }

    /**
     * 刷新JWT
     * @param refreshToken
     * @return
     */
    @GetMapping("/token/refresh")
    public WebResponse refreshToken(@RequestParam String refreshToken){
        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
        String userName = (String)stringRedisTemplate.opsForHash().get(refreshTokenKey,
                "userName");
        if(StringUtils.isBlank(userName)){
            return WebResponse.create().fail("10001","refreshToken过期");
        }
        String newToken = buildJWT(userName);
        //替换当前token，并将旧token添加到黑名单
        String oldToken = (String)stringRedisTemplate.opsForHash().get(refreshTokenKey,
                "token");
        stringRedisTemplate.opsForHash().put(refreshTokenKey, "token", newToken);
        stringRedisTemplate.opsForValue().set(String.format(jwtBlacklistKeyFormat, oldToken), "",
                tokenExpireTime, TimeUnit.MILLISECONDS);
        return WebResponse.create().success(newToken);
    }

    private String buildJWT(String userName){
        //生成jwt
        Date now = new Date();
        Algorithm algo = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("MING")
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + tokenExpireTime))
                .withClaim("userName", userName)//保存身份标识
                .sign(algo);
        return token;
    }

}