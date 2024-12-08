package com.moloom.img.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: moloom
 * @date: 2024-12-09 00:38
 * @description: 用于 bean 的注册
 */
@Configuration
public class BeanRegister {

    @Value("${moimg.host}")
    private String host;

    /**
     * @return
     * @author moloom
     * @date 2024-12-09 00:49:18
     * @description 把服务器的域名注册到容器内
     */
    @Bean("serverHost")
    public String host() {
        return host;
    }
}
