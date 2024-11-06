package com.climate_action_map.backend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climate_action_map.backend.models.ScrapeMetadata;
import com.climate_action_map.backend.services.MetadataService;

@RestController
public class MetadataController {
	
	//Get variables and other imports
	private final MetadataService metadataService;
	
	//Use Autowired to inject variables
	@Autowired
	public MetadataController(MetadataService metadataService) {
		this.metadataService = metadataService;
	}
	
	@GetMapping("/metadata")
	public ResponseEntity<Map<String, Object>> latestMetadata() {
		Optional<ScrapeMetadata> metadataOptional = metadataService.getLatestScrapeDate();

		if (metadataOptional.isPresent()) {
			ScrapeMetadata metadata = metadataOptional.get();
			Map<String, Object> response = new HashMap<>();
			response.put("lastScrapeDate", metadata.getLastScrapeDate());
			response.put("scrapeCompleted", metadata.getScrapeCompleted());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			// Return a not-found status if metadata is unavailable
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
