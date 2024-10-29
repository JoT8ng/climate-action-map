package com.climate_action_map.backend.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("secrets")
public record ConfigProperties(
		String serpapi,
		String devurl,
		String produrl,
		String environment) {

}
