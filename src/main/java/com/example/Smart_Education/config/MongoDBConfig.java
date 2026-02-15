package com.example.Smart_Education.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB Configuration Class
 * Enables MongoDB repositories and configures the connection
 */
@Configuration
@EnableMongoRepositories(
    basePackages = "com.example.Smart_Education.repository.mongodb"
)
public class MongoDBConfig {
    // MongoDB configuration is handled by Spring Boot auto-configuration
    // using properties defined in application.properties
}
