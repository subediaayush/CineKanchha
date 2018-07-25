package com.cinekancha.entities;

import com.google.gson.annotations.SerializedName;

public class GalleryItem {
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("image_url")
	private String imageUrl;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
}
