package com.moloom.img.api.service.impl;

import com.moloom.img.api.dao.TokensDao;
import com.moloom.img.api.entity.TokensEntity;
import com.moloom.img.api.service.TokensService;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.MoUtils;
import com.moloom.img.api.utils.StringGenerator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    @PostConstruct
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
    public R registerInRedis() {
        String token;
        while (true) {
            // 生成一个token
            token = StringGenerator.getToken();
            // 先占坑，尝试在 Redis 中添加
            Boolean isSet = redisTemplate.opsForValue()
                    .setIfAbsent(tokensPrefix + token, TokensEntity.builder()
                            .token(token)
                            .status((byte) 1)
                            .build(), Duration.ofDays(3l));
            // 验证token是否已被注册，如果已被注册，则重新生成一个
            if (Boolean.TRUE.equals(isSet)) {
                // 若 token 已存在数据库中，则删除 Redis 锁，重新生成一个
                if (this.isExistInDB(token))
                    // 删除 Redis 锁
                    redisTemplate.delete(tokensPrefix + token);
                else
                    return R.success().setData(Map.of("token", token));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, timeout = 5)
    @Override
    public void ensureTokenStoredAndCorrectStatus(@NotNull TokensEntity token) throws Exception {
        //查DB是否存在，不存在则插入
        if (!this.isExistInDB(token.getToken()))
            tokensDao.insert(token);
        //修改 status 为 2
        if (token.getStatus() < 2)
            tokensDao.update(token.setStatus((byte) 2));
        //remove current token from redis.
        redisTemplate.delete(tokensPrefix + token.getToken());
    }

    @Override
    public boolean isExistInDB(String token) {
        if (token == null || token.isBlank())
            return false;
        return tokensDao.selectOneByToken(token) == null ? false : true;
    }

    @Override
    public boolean checkAndCacheToken(String token) {
        if (token == null || !StringGenerator.validateToken(token))
            return false;
        //TODO 需要优化，在多线程下，可能存在并发问题
        TokensEntity tokensEntity;
        //查看 redis 中是否有 token
        tokensEntity = (TokensEntity) redisTemplate.opsForValue().get(tokensPrefix + token);
        if (tokensEntity == null)
            //若 redis 里不存在，则查 DB
            tokensEntity = tokensDao.selectOneByToken(token);
        //若已过期，或不存在，则返回 false
        if (tokensEntity == null || tokensEntity.getStatus() == 0)
            return false;
        //若存在，则更新 cache 过期时间为 status*8 天
        redisTemplate.opsForValue().set(tokensPrefix + token, tokensEntity, Duration.ofDays(tokensEntity.getStatus() << 3));
        //token 正常，返回true
        return true;
    }
}
