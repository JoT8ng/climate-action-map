package com.climate_action_map.backend.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.climate_action_map.backend.models.ProcessedData;
import com.climate_action_map.backend.utils.ConfigProperties;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class GeocodingService {
	
	// Get variables and other imports
	private final ConfigProperties secretsConfig;
	private final RestTemplate restTemplate;
	
	// Use Autowired to inject variables
	@Autowired
	public GeocodingService(ConfigProperties secretsConfigProperties, RestTemplate restTemplate) {
		this.secretsConfig = secretsConfigProperties;
		this.restTemplate = restTemplate;
	}

	// Method to geocode address data into coordinatess
	public ProcessedData.Location GeocodeData(String address) {
		try {
			String apiKey = secretsConfig.geocodeapi();
			
			// URL-encode the address
	        String addressUrl = URLEncoder.encode(address, "UTF-8");
			
			// Prepare api request query string
			String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + addressUrl + "&key=" + apiKey;
			
			// Google geocoding api request
			String response = restTemplate.getForObject(url, String.class);
			
	        // Parse the response to JSON
			JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonObject location = jsonResponse
                    .getAsJsonArray("results")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("geometry")
                    .getAsJsonObject("location");

            double latitude = location.get("lat").getAsDouble();
            double longitude = location.get("lng").getAsDouble();
			
			// Return Geocoded location
            return new ProcessedData.Location(latitude, longitude);
		} catch (UnsupportedEncodingException e) {
            System.err.println("Error encoding address: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error in geocoding API response: " + e.getMessage());
            return null;
        }
	}
}
