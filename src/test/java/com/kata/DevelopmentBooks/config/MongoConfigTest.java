package com.kata.DevelopmentBooks.config;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;

class MongoConfigTest {

    private MongoProperties mongoProperties;

    private MongoConfig mongoConfig;

    @BeforeEach
    void setUp() {
        mongoProperties = new MongoProperties();
        mongoProperties.setUsername("admin");
        mongoProperties.setPassword("admin");
        mongoProperties.setHost("cluster1.mongodb.net");
        mongoProperties.setDatabase("bookstore");
        mongoProperties.setOptions("?retryWrites=true&w=majority");

        mongoConfig = new MongoConfig(mongoProperties);
    }

    @Test
    @DisplayName("test Mongo client is not null")
    void testMongoClientCreation() {
        MongoClient client = mongoConfig.mongoClient();
        assertNotNull(client, "MongoClient should not be null");
        client.close();
    }

    @Test
    @DisplayName("test Mongo template is not null")
    void testMongoTemplateCreation() {
        MongoTemplate template = mongoConfig.mongoTemplate();
        assertNotNull(template, "MongoTemplate should not be null");
    }
}