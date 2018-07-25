package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

public class BoxOfficeItem {
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("name")
	private String movie;
	
	@SerializedName("total_collected")
	private String collected;
	
	public long getId() {
		return id;
	}
	
	public String getMovie() {
		return movie;
	}
	
	public String getCollected() {
		return collected;
	}
}
