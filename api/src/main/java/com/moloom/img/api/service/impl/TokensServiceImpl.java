package com.moloom.img.api.service.impl;

import com.moloom.img.api.dao.TokensDao;
import com.moloom.img.api.entity.Tokens;
import com.moloom.img.api.service.TokensService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.MoUtils;
import com.moloom.img.api.utils.StringGenerator;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

/**
 * @author: moloom
 * @date: 2024-11-27 16:27
 * @description: tokens service impl
 */
@Service("tokensService")
@Slf4j
public class TokensServiceImpl implements TokensService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private TokensDao tokensDao;


    //redis中 token 的key前缀
    @Resource
    private String tokensPrefix;


    /**
     * @author moloom
     * @date 2024-11-27 16:57:56
     * @description 启动时，将数据库中已存在的token加载到redis中
     */
    @PostConstruct
    public void preloadTokensToRedis() {
        try {
            tokensDao.selectAll().forEach(token -> {
                if (token.getStatus() != 0)
                    //根据token的状态设置过期时间，过期时间=(status*4)+-(status*2)
                    redisTemplate.opsForValue().set(tokensPrefix + token.getToken(), token, Duration.ofDays(MoUtils.randomDays(token.getStatus() << 2)));
            });
        } catch (Exception e) {
            log.error("preload tokens to redis is error");
            throw new RuntimeException(e);
        }
    }


    @Override
    public R register() {
        String token;
        while (true) {
            // 生成一个token
            token = StringGenerator.getToken();
            // 先占坑，尝试在 Redis 中添加
            Boolean isSet = redisTemplate.opsForValue()
                    .setIfAbsent(tokensPrefix + token, Tokens.builder()
                            .token(token)
                            .status((byte) 1)
                            .createdBy(1L)
                            .build(), Duration.ofDays(3l));
            // 验证token是否已被注册，如果已被注册，则重新生成一个
            if (Boolean.TRUE.equals(isSet)) {
                // 若 token 已存在数据库中，则删除 Redis 锁，重新生成一个
                if (this.isExist(token))
                    // 删除 Redis 锁
                    redisTemplate.delete(tokensPrefix + token);
                else
                    return R.success().setData(Map.of("token", token));
            }
        }
    }

    @Override
    public boolean isExist(String token) {
        if (token == null || token.isBlank())
            return false;
        return tokensDao.selectOneByToken(token) == null ? false : true;
    }
}
