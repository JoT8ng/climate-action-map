package com.climate_action_map.backend.services;

import java.time.LocalDateTime;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.climate_action_map.backend.models.RawData;
import com.climate_action_map.backend.repositories.RawDataRepository;
import com.climate_action_map.backend.utils.ConfigProperties;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ScraperService {

	//Get variables and other imports
	private final ConfigProperties secretsConfig;
	private final RestTemplate restTemplate;
	private final RawDataRepository rawDataRepository;
	private final MetadataService metadataService;
	
	//Use Autowired to inject variables
	@Autowired
	public ScraperService(ConfigProperties secretsConfigProperties, RestTemplate restTemplate, RawDataRepository rawDataRepository, MetadataService metadataService) {
		this.secretsConfig = secretsConfigProperties;
		this.restTemplate = restTemplate;
		this.rawDataRepository = rawDataRepository;
		this.metadataService = metadataService;
	}
	
	public JsonArray ScrapeData() {
		String apiKey = secretsConfig.serpapi();
	    String url = "https://serpapi.com/search.json?engine=google_events&q=Climate+events+in+Toronto+this+month&gl=ca&hl=en&api_key=" + apiKey;
	    
	    Boolean scrapeSuccess = false;
	    
	    try {
		    // Fetch data from the serpapi URL
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
	        
	        scrapeSuccess = true;
	        
	        return eventsResults;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	// Update metadata
	    	metadataService.updateMetadata(scrapeSuccess);
	    }
	    
	    // Return empty JsonArray in case of failure
	    return new JsonArray();
	}
}
