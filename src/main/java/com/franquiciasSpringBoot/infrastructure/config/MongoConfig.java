package com.franquiciasSpringBoot.infrastructure.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.franquiciasSpringBoot.infrastructure.adapters.mongodb") // <--- Revisa que esta ruta sea exacta
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        // Aquí obligamos a que devuelva "db_franquicias"
        return databaseName;
    }

    @Override
    public MongoClient reactiveMongoClient() {
        // Aquí obligamos a que use la URI de localhost:27017
        return MongoClients.create(mongoUri);
    }

}