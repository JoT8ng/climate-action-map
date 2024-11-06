package com.climate_action_map.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Sort;

import com.climate_action_map.backend.models.ScrapeMetadata;

public interface ScrapeMetadataRepository extends MongoRepository<ScrapeMetadata, String> {
	
	// Custom query to find latest entry based on `last_scrape_date`
    default Optional<ScrapeMetadata> findLatest() {
        return findAll(Sort.by(Sort.Direction.DESC, "last_scrape_date")).stream().findFirst();
    }

}
