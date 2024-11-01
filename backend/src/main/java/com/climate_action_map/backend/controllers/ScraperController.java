package com.climate_action_map.backend.controllers;

import com.climate_action_map.backend.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hw.serpapi.GoogleSearch;
import com.hw.serpapi.SerpApiSearch;
import com.hw.serpapi.SerpApiSearchException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ScraperController {
	
	//Get variables in ConfigProperties
	private final ConfigProperties secretsConfig;
	
	//Use Autowired to inject variables in ConfigProperties
	@Autowired
	public ScraperController(ConfigProperties secretsConfigProperties) {
		this.secretsConfig = secretsConfigProperties;
	}
	
	@GetMapping("/scraper")
	public ResponseEntity<String> scrapeEvents() {
		Map<String, String> parameter = new HashMap<>();
		
		// parameters
		parameter.put("engine", "google_events");
		parameter.put("q", "Climate events in Toronto next month");
		parameter.put("hl", "en");
		parameter.put("gl", "us");
		parameter.put("api_key", secretsConfig.serpapi());

		// create search
		SerpApiSearch search = new GoogleSearch(parameter);

		try {
			// execute search
			JsonObject results = search.getJson();
			
			// Extract event results array
            JsonArray eventResults = results.getAsJsonArray("events_results");
            
            // Convert JSON array to String for simplicity
            String searchResult = eventResults.toString();

            // Return the result as JSON String
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
		} catch (SerpApiSearchException ex) {
			System.out.println("Exception:");
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
