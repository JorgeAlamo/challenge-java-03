package com.business.service.favorites;

import io.r2dbc.spi.ConnectionFactory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@OpenAPIDefinition(
		info = @Info(
				title = "Favorite Service",
				version = "1.0",
				description = "Documentation Favorite Service API v1.0"))
@SecurityScheme(
		name = "bearerToken",
		scheme = "bearer",
		bearerFormat = "JWT",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER)
@EnableEurekaClient
@SpringBootApplication
public class FavoritesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritesApplication.class, args);
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("dbsetup.sql")));
		return initializer;
	}

}
