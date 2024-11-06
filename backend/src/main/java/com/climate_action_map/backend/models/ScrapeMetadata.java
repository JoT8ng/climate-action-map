package com.climate_action_map.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ScrapeMetadata")
public class ScrapeMetadata {

	@Id
	private String id;
	private String last_scrape_date;
	private Boolean scrape_completed = true;
	
	// No argument constructor
	public ScrapeMetadata() {
		this.scrape_completed = true;
	}
	
	// Constructor with parameters for convenience
	public ScrapeMetadata(String id, String last_scrape_date, Boolean scrape_completed) {
		this.id = id;
		this.last_scrape_date = last_scrape_date;
		this.scrape_completed = true;
	}
	
	// Getters and setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLastScrapeDate() {
		return last_scrape_date;
	}
	
	public void setLastScrapeDate(String last_scrape_date) {
		this.last_scrape_date = last_scrape_date;
	}
	
	public Boolean getScrapeCompleted() {
		return scrape_completed;
	}
	
	public void setScrapeCompleted(Boolean scrape_completed) {
		this.scrape_completed = scrape_completed;
	}
}
