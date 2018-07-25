package com.cinekancha.entities;

import com.google.gson.annotations.SerializedName;

public class Video {
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("image_url")
	private String imageUrl;
	
	@SerializedName("name")
	private String title;
	
	private String link;
	
	public long getId() {
		return id;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getTitle() {
		return title;
	}
}
