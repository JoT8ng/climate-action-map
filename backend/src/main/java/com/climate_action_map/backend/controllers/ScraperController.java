package com.climate_action_map.backend.controllers;

import com.climate_action_map.backend.models.RawData;
import com.climate_action_map.backend.repositories.RawDataRepository;
import com.climate_action_map.backend.utils.ConfigProperties;

import org.bson.Document;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ScraperController {
	
	//Get variables and other imports
	private final ConfigProperties secretsConfig;
	private final RestTemplate restTemplate;
	private final RawDataRepository rawDataRepository;
	
	//Use Autowired to inject variables
	@Autowired
	public ScraperController(ConfigProperties secretsConfigProperties, RestTemplate restTemplate, RawDataRepository rawDataRepository) {
		this.secretsConfig = secretsConfigProperties;
		this.restTemplate = restTemplate;
		this.rawDataRepository = rawDataRepository;
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
	        
	        // Convert eventsResults to string for storage in database
	        String eventsJson = results.toString();
	        // Create new RawData object with fields to store in Mongo database
	        RawData rawData = new RawData();
	        rawData.setScraped_at(LocalDateTime.now().toString());
	        rawData.setSource_data(Document.parse(eventsJson));
	        rawData.setIsprocessed(false);
	        
	        // Save RawData to MongoDB
	        rawDataRepository.save(rawData);
	
	        // Convert JsonArray to List for ResponseEntity compatibility
	        return new ResponseEntity<>(new Gson().fromJson(eventsResults, List.class), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println("Exception:");
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
