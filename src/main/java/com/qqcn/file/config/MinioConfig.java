package com.qqcn.file.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${wjj.minio.endpoint}")
    private String endpoint;
    @Value("${wjj.minio.port}")
    private Integer port;
    @Value("${wjj.minio.accessKey}")
    private String accessKey;
    @Value("${wjj.minio.secretKey}")
    private String secretKey;
    @Value("${wjj.minio.secure}")
    private Boolean secure;


    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint,port,secure)
                .credentials(accessKey, secretKey)
                .build();
    }
}