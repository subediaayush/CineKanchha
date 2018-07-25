package com.cinekancha.entities;

import android.support.annotation.DrawableRes;

public class ThumbWrapper {
	private String imageUrl;
	@DrawableRes private int imageRes = -1;
	private String caption;
	private String subCaption;
	
	public ThumbWrapper(String imageUrl, String caption, String subCaption) {
		this(imageUrl, -1, caption, subCaption);
	}
	
	public ThumbWrapper(String imageUrl, String caption) {
		this(imageUrl, -1, caption, "");
	}
	
	public ThumbWrapper(@DrawableRes int imageRes, String caption, String subCaption) {
		this("", imageRes, caption, subCaption);
	}
	
	public ThumbWrapper(@DrawableRes int imageRes, String caption) {
		this("", imageRes, caption, "");
	}
	
	private ThumbWrapper(String imageUrl, int imageRes, String caption, String subCaption) {
		this.imageUrl = imageUrl;
		this.imageRes = imageRes;
		this.caption = caption;
		this.subCaption = subCaption;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public int getImageRes() {
		return imageRes;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getSubCaption() {
		return subCaption;
	}
}
