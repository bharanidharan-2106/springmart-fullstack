package com.springmart.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoAuditConfig {
    // This enables @CreatedDate and @LastModifiedDate auditing annotations on entities
}
