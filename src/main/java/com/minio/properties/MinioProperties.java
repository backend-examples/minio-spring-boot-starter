package com.minio.properties;

import com.minio.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = PropertyConstant.prefix)
public class MinioProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucket = PropertyConstant.bucket;
}
