package com.data.filtro.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Add the @Configuration annotation to this class so that Spring Boot can scan the @Bean definitions within the class.
public class MinioConfig {
    @Value("${spring.data.minio.url}")
    private String url;

    @Value("${spring.data.minio.accessKey}")
    private String accessKey;

    @Value("${spring.data.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
