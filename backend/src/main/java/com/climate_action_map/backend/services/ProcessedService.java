package com.climate_action_map.backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climate_action_map.backend.models.ProcessedData;
import com.climate_action_map.backend.models.RawData;
import com.climate_action_map.backend.repositories.ProcessedDataRepository;
import com.climate_action_map.backend.repositories.RawDataRepository;

@Service
public class ProcessedService {

	//Get variables and other imports
	private final ProcessedDataRepository processedDataRepository;
	private final RawDataRepository rawDataRepository;
	private final GeocodingService geocodingService;
	
	//Use Autowired to inject variables
	@Autowired
	public ProcessedService(ProcessedDataRepository processedDataRepository, RawDataRepository rawDataRepository, GeocodingService geocodingService) {
		this.processedDataRepository = processedDataRepository;
		this.rawDataRepository = rawDataRepository;
		this.geocodingService = geocodingService;
	}
	
	//Method to process latest raw scraped data
	public void processData() {
		//Get latest raw scraped data
		Optional<RawData> rawdataOptional = rawDataRepository.findLatest();
		
		//Check if isProcessed is false
		if (rawdataOptional.isPresent() && !rawdataOptional.get().getIsprocessed()) {
			RawData rawdata = rawdataOptional.get();
	        // Access `source_data` document
	        Document sourceData = rawdata.getSource_data();
	        // Create an empty list to store all processed events
	        List<ProcessedData.Event> events = new ArrayList<>();
	        // Access `events_results` array from `source_data`
	        List<Document> eventsResults = (List<Document>) sourceData.get("events_results");
	        
	        if (eventsResults != null) {
	            for (Document eventDoc : eventsResults) {
	                // Extract individual fields from each event document
	                String event_name = (String) eventDoc.get("title");
	                Document dateDoc = (Document) eventDoc.get("date");
	                String event_date = dateDoc != null ? (String) dateDoc.get("start_date") : null;
	                // Concatenate address array into a single string
	                List<String> addressList = (List<String>) eventDoc.get("address");
	                String address = addressList != null ? String.join(", ", addressList) : null;
	                String description = (String) eventDoc.get("description");

	                // Geocode and set location
	                ProcessedData.Location location = geocodingService.GeocodeData(address);

	                // Create a new Event object and add it to the list
	                ProcessedData.Event event = new ProcessedData.Event(event_name, event_date, address, description, location);
	                events.add(event);
	            }
	        }
	        
			ProcessedData processed = new ProcessedData();
			processed.setProcessedDate(LocalDateTime.now().toString());
			processed.setEvents(events);
			
			//Save processed data
			processedDataRepository.save(processed);
			
			//Mark the raw data as processed
            rawdata.setIsprocessed(true);
            rawDataRepository.save(rawdata);
		}
	}
}
