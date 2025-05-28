package com.kata.bookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bookstore.mongo")
@Data
public class MongoProperties {
    private String username;
    private String password;
    private String host;
    private String database;
    private String options;
}
