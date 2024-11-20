package com.climate_action_map.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climate_action_map.backend.models.ProcessedData;
import com.climate_action_map.backend.services.ProcessedService;

@RestController
public class DataController {
	
	//Get variables and other imports
	private final ProcessedService processedService;
	
	//Use Autowired to inject variables
	@Autowired
	public DataController(ProcessedService processedService) {
		this.processedService = processedService;
	}
	
	@GetMapping("/data")
	public ResponseEntity<ProcessedData> getEventData() {
		Optional<ProcessedData> dataOptional = processedService.getLatestProcessedData();

		if (dataOptional.isPresent()) {
			ProcessedData data = dataOptional.get();
			return new ResponseEntity<>(data, HttpStatus.OK);
		} else {
			// Return a not-found status if metadata is unavailable
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
