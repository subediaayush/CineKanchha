package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class Article implements Serializable{
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("banner_url")
	private String image;
	
	@SerializedName("title")
	private String title;
	
	@SerializedName("summary")
	private String summary;
	
	@SerializedName("content")
	private String content;
	
	@SerializedName("featured")
	private boolean featured;
	
	@SerializedName("url")
	private String url;
	
	@SerializedName("author")
	private String author;
	
	@SerializedName("created_at")
	private String createdAt;
	
	@SerializedName("updated_at")
	private String updatedAt;
	
	public int getId() {
		return id;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean isFeatured() {
		return featured;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public String getUpdatedAt() {
		return updatedAt;
	}
}
