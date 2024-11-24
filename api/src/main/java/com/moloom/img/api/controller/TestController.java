package com.moloom.img.api.controller;

import com.moloom.img.api.to.R;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author: moloom
 * @date: 2024-09-23 21:20
 * @description:
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TestController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private MinioClient minioClient;

    @RequestMapping(value = "/i/test", method = RequestMethod.GET)
    public R TestConnection() throws Exception {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间为字符串
        String formattedTime = currentTime.format(formatter);


        //测试minio连接
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("images").build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("images").build());
            log.info("already make bucket images");
        } else
            log.info("bucket images is exists");
        //测试redis连接
        redisTemplate.opsForValue().set("currentTime", formattedTime);
        return R.success("test is success!!").setData(Map.of("currentTime", formattedTime));
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        if ("moloom".equals(username) && "pass".equals(password))
            return "true";
        else return "false";
    }
}
