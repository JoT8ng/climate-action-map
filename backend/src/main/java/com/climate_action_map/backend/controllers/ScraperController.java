package com.climate_action_map.backend.controllers;

import com.climate_action_map.backend.services.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.util.List;

@RestController
public class ScraperController {
	
	//Get variables and other imports
	private final ScraperService scraperService;
	
	//Use Autowired to inject variables
	@Autowired
	public ScraperController(ScraperService scraperService) {
		this.scraperService = scraperService;
	}
	
	@GetMapping("/scraper")
	public ResponseEntity<Object> scrapeEvents() {
	    try {
	        // Fetch data from serpapi Scraper Service and save to database
	    	JsonArray eventsResults = scraperService.ScrapeData();
	    	
	    	// Update scrape metadata database
	    	
	    	// Process and Geocode raw scraped data and save to database
	
	        // Convert JsonArray to List for ResponseEntity compatibility
	        return new ResponseEntity<>(new Gson().fromJson(eventsResults, List.class), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println("Exception:");
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
