package com.climate_action_map.backend.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climate_action_map.backend.models.ScrapeMetadata;
import com.climate_action_map.backend.repositories.ScrapeMetadataRepository;

@Service
public class MetadataService {
	
	//Get variables and other imports
	private final ScrapeMetadataRepository scrapeMetadataRepository;
	
	//Use Autowired to inject variables
	@Autowired
	public MetadataService(ScrapeMetadataRepository scrapeMetadataRepository) {
		this.scrapeMetadataRepository = scrapeMetadataRepository;
	}
	
	// Method to get latest scrape metadata
	public Optional<String> getLastScrapeDate() {
        return scrapeMetadataRepository.findLatest()
                                       .map(ScrapeMetadata::getLastScrapeDate);
    }
	
	// Method to update and save metadata once data is scraped
	public void updateMetadata(Boolean scrapeCompleted) {
		ScrapeMetadata metadata = new ScrapeMetadata();
		metadata.setLastScrapeDate(LocalDateTime.now().toString());
		metadata.setScrapeCompleted(scrapeCompleted);
		
		scrapeMetadataRepository.save(metadata);
	}

}
