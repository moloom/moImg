package com.moloom.img.api.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: moloom
 * @date: 2024-10-05 20:29
 * @description: init a singleton MinioClient bean
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio.server")
public class MinioConfig {

    private String host;

    private String accessKey;

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
