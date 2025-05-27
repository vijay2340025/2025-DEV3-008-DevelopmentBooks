package com.kata.DevelopmentBooks.config;

import com.mongodb.client.MongoClient;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private final MongoProperties properties;

    public MongoConfig(MongoProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = getConnectionString();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(settings);
    }

    private ConnectionString getConnectionString() {
        String uri = String.format("mongodb+srv://%s:%s@%s/%s%s",
                properties.getUsername(),
                properties.getPassword(),
                properties.getHost(),
                properties.getDatabase(),
                properties.getOptions());

        return new ConnectionString(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), properties.getDatabase());
    }
}
