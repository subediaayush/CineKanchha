package com.cinekancha.entities.model;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedItem {
	private String imageUrl;
	private String link;
	private String deeplink;
	private String description;
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getDeeplink() {
		return deeplink;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
