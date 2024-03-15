package com.minio.config;

import com.minio.properties.MinioProperties;
import com.minio.utils.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfigure {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioTemplate minioTemplate() {
        return new MinioTemplate(minioProperties);
    }
}
