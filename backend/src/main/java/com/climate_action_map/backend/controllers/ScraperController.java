package com.climate_action_map.backend.controllers;

import com.climate_action_map.backend.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

@RestController
public class ScraperController {
	
	//Get variables
	private final ConfigProperties secretsConfig;
	private final RestTemplate restTemplate;
	
	//Use Autowired to inject variables
	@Autowired
	public ScraperController(ConfigProperties secretsConfigProperties, RestTemplate restTemplate) {
		this.secretsConfig = secretsConfigProperties;
		this.restTemplate = restTemplate;
	}
	
	@GetMapping("/scraper")
	public ResponseEntity<Object> scrapeEvents() {
		String apiKey = secretsConfig.serpapi();
	    String url = "https://serpapi.com/search.json?engine=google_events&q=Climate+events+in+Toronto+this+month&gl=ca&hl=en&api_key=" + apiKey;
	
	    try {
	        // Fetch data from the URL
	        String response = restTemplate.getForObject(url, String.class);
	
	        // Parse the response to JSON
	        JsonObject results = JsonParser.parseString(response).getAsJsonObject();
	
	        // Extract "events_results" from JSON response
	        JsonArray eventsResults = results.getAsJsonArray("events_results");
	
	        // Convert JsonArray to List for ResponseEntity compatibility
	        return new ResponseEntity<>(new Gson().fromJson(eventsResults, List.class), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println("Exception:");
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
