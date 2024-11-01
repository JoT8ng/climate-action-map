package com.climate_action_map.backend.controllers;

import com.climate_action_map.backend.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import serpapi.GoogleSearch;
import serpapi.SerpApiSearchException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
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
	public ResponseEntity<Object> scrapeEvents() {
		Map<String, String> parameter = new HashMap<>();
		
		// parameters
		parameter.put("engine", "google_events");
		parameter.put("q", "Climate events in Toronto");
		parameter.put("google_domain", "google.ca");
		parameter.put("hl", "en");
		parameter.put("gl", "ca");
		parameter.put("location", "Toronto, Ontario, Canada");
		parameter.put("api_key", secretsConfig.serpapi());

		// create search
		GoogleSearch search = new GoogleSearch(parameter);

		try {
			// execute search
			JsonObject results = search.getJson();
			
			// Extract events_results from JSON and convert it to a compatible type
	        JsonArray eventsResults = results.getAsJsonArray("events_results");
	        
	        // Convert JsonArray to a list for easier use in the ResponseEntity
	        return new ResponseEntity<>(new Gson().fromJson(eventsResults, List.class), HttpStatus.OK);
		} catch (SerpApiSearchException ex) {
			System.out.println("Exception:");
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
