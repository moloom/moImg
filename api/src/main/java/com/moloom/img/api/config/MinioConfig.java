package com.moloom.img.api.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: moloom
 * @date: 2024-10-05 20:29
 * @description: init a singleton MinioClient bean
 */
@Configuration
public class MinioConfig {

    @Value("${minio.host}")
    private String host;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient initMinio() {
        // Create a minioClient with the MinIO server playground, its access key and secret key.
        return MinioClient.builder()
                .endpoint(host)
                .credentials(accessKey, secretKey)
                .build();
    }

}
