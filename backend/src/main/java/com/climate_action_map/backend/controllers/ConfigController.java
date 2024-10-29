package com.climate_action_map.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climate_action_map.backend.utils.ConfigProperties;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

		private final ConfigProperties secretsConfig;
		
		public ConfigController(ConfigProperties secretsConfigProperties) {
			this.secretsConfig = secretsConfigProperties;
		}
		
		@GetMapping
		public Map<String,String> printAllProps() {
			return Map.of("key", secretsConfig.serpapi(),
					"devURL", secretsConfig.devurl(),
					"prodURL", secretsConfig.produrl(),
					"environment", secretsConfig.environment());
		}
}
