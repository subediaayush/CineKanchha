package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class Troll {
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("image_url")
	private String image;
	
	public String getImage() {
		return image;
	}
	
	public long getId() {
		return id;
	}
}
