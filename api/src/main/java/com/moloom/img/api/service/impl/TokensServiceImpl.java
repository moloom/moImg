package com.moloom.img.api.service.impl;

import com.moloom.img.api.dao.TokensDao;
import com.moloom.img.api.entity.Tokens;
import com.moloom.img.api.service.TokensService;
import com.moloom.img.api.to.R;
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
    //tokens 有效期  天
    private static final String TOKEN_PREFIX_OF_REDIS = "tokens:all:";





    @Override
    public R register() {
        // 生成一个token
        String token = StringGenerator.getToken();
        // 验证token是否已被注册，如果已被注册，则重新生成一个
        while (redisTemplate.hasKey(TOKEN_PREFIX_OF_REDIS + token) || this.isExist(token)) {
            token = StringGenerator.getToken();
        }
        // 将token存入redis
        try {
            redisTemplate.opsForValue().set(TOKEN_PREFIX_OF_REDIS + token, Tokens.builder()
                            .token(token)
                            .status((byte) 1)
                            .createdBy(7L)
                            .build(),
                    Duration.ofDays(1));
            return R.success().setData(Map.of("token", token));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExist(String token) {
        if (token == null || token.isBlank())
            return false;
        return tokensDao.selectOneByToken(token) > 0 ? true : false;
    }
}
