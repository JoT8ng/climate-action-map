package com.climate_action_map.backend.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.climate_action_map.backend.models.RawData;

import java.util.List;
import java.util.Optional;

public interface RawDataRepository extends MongoRepository<RawData, String> {

	// Custom method to find all documents where `is_processed` is false
    List<RawData> findByIsProcessedFalse();
    
    // Custom method to find latest scraped data
    default Optional<RawData> findLatest() {
        return findAll(Sort.by(Sort.Direction.DESC, "scraped_at")).stream().findFirst();
    }
}
