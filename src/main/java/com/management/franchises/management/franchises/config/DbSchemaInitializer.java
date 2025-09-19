package com.management.franchises.management.franchises.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
public class DbSchemaInitializer {

    private static final String CREATE_FRANCHISES = """
        CREATE TABLE IF NOT EXISTS franchises (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL
        );
    """;

    private static final String CREATE_BRANCHES = """
        CREATE TABLE IF NOT EXISTS branches (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            franchise_id BIGINT
        );
    """;

    private static final String CREATE_PRODUCTS = """
        CREATE TABLE IF NOT EXISTS products (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            stock BIGINT,
            branch_id BIGINT
        );
    """;

    @Bean
    public CommandLineRunner initDb(DatabaseClient db) {
        return args -> {
            Mono<Void> setup = db.sql(CREATE_FRANCHISES).then()
                .then(db.sql(CREATE_BRANCHES).then())
                .then(db.sql(CREATE_PRODUCTS).then());

            setup.subscribe(
                null,
                error -> System.err.println("Error al crear tablas: " + error.getMessage()),
                () -> System.out.println("Tablas creadas exitosamente.")
            );
        };
    }
}

