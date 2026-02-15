package com.example.Smart_Education.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Configuration Class for MySQL
 * Enables JPA repositories for MySQL database
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.Smart_Education.repository.mysql",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class JPAConfig {
    // JPA configuration is handled by Spring Boot auto-configuration
    // using properties defined in application.properties
}
