package com.climate_action_map.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.climate_action_map.backend.models.ProcessedData;

public interface ProcessedDataRepository extends MongoRepository<ProcessedData, String> {

	// Custom method to find latest processed data
    default Optional<ProcessedData> findLatest() {
        return findAll(Sort.by(Sort.Direction.DESC, "processed_date")).stream().findFirst();
    }
}
