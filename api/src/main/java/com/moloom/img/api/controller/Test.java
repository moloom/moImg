package com.moloom.img.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.moloom.img.api.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: moloom
 * @date: 2024-09-23 21:20
 * @description:
 */
@RestController
@RequestMapping("/i")
public class Test {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String TestConnection() {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前时间为字符串
        String formattedTime = currentTime.format(formatter);

        redisTemplate.opsForValue().set("currentTime", formattedTime);
        return JSONArray.toJSONString("Welcome to moImg!");
    }
}
