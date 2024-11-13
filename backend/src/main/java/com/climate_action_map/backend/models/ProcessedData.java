package com.climate_action_map.backend.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ProcessedData")
public class ProcessedData {

	@Id
	private String id;
	private List<Event> events;
	private String processed_date;
	
	// No argument constructor
	public ProcessedData() {
	}
	
	// Constructor with parameters for convenience
	public ProcessedData(String id, List<Event> events, String processed_date) {
		this.id = id;
		this.events = events;
		this.processed_date = processed_date;
	}
	
	// Getters and setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
	
	public String getProcessedDate() {
		return processed_date;
	}
	
	public void setProcessedDate(String processed_date) {
		this.processed_date = processed_date;
	}
    
    // Nested Event class for events
    public static class Event {
    	private String event_name;
    	private String event_date;
    	private String address;
    	private String description;
    	private Location location;
    	
    	public Event() {
    	}
    	
    	public Event(String event_name, String event_date, String address, String description, Location location) {
    		this.event_name = event_name;
    		this.event_date = event_date;
    		this.address = address;
    		this.description = description;
    		this.location = location;
    	}
    	
    	public String getEventName() {
    		return event_name;
    	}
    	
    	public void setEventName(String event_name) {
    		this.event_name = event_name;
    	}
    	
    	public String getEventDate() {
    		return event_date;
    	}
    	
    	public void setEventDate(String event_date) {
    		this.event_date = event_date;
    	}
    	
    	public String getAddress() {
    		return address;
    	}
    	
    	public void setAddress(String address) {
    		this.address = address;
    	}
    	
    	public String getDescription() {
    		return description;
    	}
    	
    	public void setDescription(String description) {
    		this.description = description;
    	}
    	
    	public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }
		
    // Nested Location class for latitude and longitude
    public static class Location {
        private double latitude;
        private double longitude;

        public Location() {
        }

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        // Getters and setters
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
