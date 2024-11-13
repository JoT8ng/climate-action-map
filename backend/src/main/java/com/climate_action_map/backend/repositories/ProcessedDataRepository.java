package com.climate_action_map.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.climate_action_map.backend.models.ProcessedData;

public interface ProcessedDataRepository extends MongoRepository<ProcessedData, String> {

	// Custom queries or methods
}
