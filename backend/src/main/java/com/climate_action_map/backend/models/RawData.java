package com.climate_action_map.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RawData")
public class RawData {
	
	@Id
	private String id;
	private String scraped_at;
	private org.bson.Document source_data;
	private Boolean isProcessed = false;
	
	// No argument constructor
	public RawData() {
		this.isProcessed = false;
	}
	
	// Constructor with parameters for convenience
	public RawData(String id, String scraped_at, String sourceDataJson, Boolean isProcessed) {
		this.id = id;
		this.scraped_at = scraped_at;
		this.source_data = org.bson.Document.parse(sourceDataJson);
		this.isProcessed = false;
	}
	
	// Getters and setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getScraped_at() {
		return scraped_at;
	}
	
	public void setScraped_at(String scraped_at) {
		this.scraped_at = scraped_at;
	}
	
	public org.bson.Document getSource_data() {
		return source_data;
	}
	
	public void setSource_data(org.bson.Document source_data) {
		this.source_data = source_data;
	}
	
	public Boolean getIsprocessed() {
		return isProcessed;
	}
	
	public void setIsprocessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

}
