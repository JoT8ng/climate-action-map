package com.climate_action_map.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	//Get variables in ConfigProperties
	private final ConfigProperties secretsConfig;
	
	//Use Autowired to inject variables in ConfigProperties
	@Autowired
	public WebConfig(ConfigProperties secretsConfigProperties) {
		this.secretsConfig = secretsConfigProperties;
	}
	
	//Apply CORS configuration to all routes
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		
		//Select frontend url based on environment
		String frontendUrl = "development".equals(secretsConfig.environment())
				? secretsConfig.devurl()
				: secretsConfig.produrl();
		
        registry.addMapping("/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST");
    }
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
