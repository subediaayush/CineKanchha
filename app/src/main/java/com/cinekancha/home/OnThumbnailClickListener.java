package com.cinekancha.home;

import com.cinekancha.entities.ThumbWrapper;

public interface OnThumbnailClickListener<T> {
	void onThumbnailClicked(T thumbnailData, ThumbWrapper wrapper);
}
