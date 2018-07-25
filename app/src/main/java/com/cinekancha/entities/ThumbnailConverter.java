package com.cinekancha.entities;

public interface ThumbnailConverter<T> {
	ThumbWrapper convert(T data);
}
