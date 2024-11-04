package com.climate_action_map.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.climate_action_map.backend.models.RawData;

import java.util.List;

public interface RawDataRepository extends MongoRepository<RawData, String> {

	// Custom method to find all documents where `is_processed` is false
    List<RawData> findByIsProcessedFalse();
    
}
