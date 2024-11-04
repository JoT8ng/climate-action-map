package com.climate_action_map.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.climate_action_map.backend.utils.ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@EnableMongoRepositories(basePackages = "com.climate_action_map.backend.repositories")
public class BackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceApplication.class, args);
	}

}
