package com.moloom.img.api.controller;

import com.moloom.img.api.entity.Tokens;
import com.moloom.img.api.to.R;
import com.moloom.img.api.utils.StringGenerator;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: moloom
 * @date: 2024-11-20 18:06
 * @description: tokens controller
 */
@Controller
@RequestMapping("/api/token")
public class TokensController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @return String token if success or error
     * @author moloom
     * @date 2024-11-20 23:39:03
     * @description generate a token and save it to redis
     */
    @PostMapping("/reg")
    @ResponseBody
    public R reg() {
        String token = StringGenerator.getToken();
        try {
            redisTemplate.opsForValue().set("token:all:" + token, Tokens.builder().token(token).status((byte) 1).createdBy(7L).build(), Duration.ofDays(1));
            return R.success().setData(Map.of("token", token));
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
