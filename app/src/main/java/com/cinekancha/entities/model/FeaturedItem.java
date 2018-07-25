package com.cinekancha.entities.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedItem {
	
	@SerializedName("id")
	private long id;
	
	@SerializedName("title")
	private String title;

	@SerializedName("subtitle")
	private String subTitle;
	
	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("deeplink")
	private String deeplink;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getDeeplink() {
		return deeplink;
	}
	
	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}
}
