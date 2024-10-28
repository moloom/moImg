package com.moloom.img.api.config;

import com.moloom.img.api.to.Buckets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author: moloom
 * @date: 2024-10-28 21:08
 * @description: 从application.yaml自动注入配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class BucketConfig {
    private ArrayList<Buckets> buckets;
}
