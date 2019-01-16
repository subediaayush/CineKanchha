package com.cinekancha.entities.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoxOfficeData {
	@SerializedName("data")
	@Expose
	private List<MovieBoxOffice> data = new ArrayList<>();
	
	public List<MovieBoxOffice> getData() {
		return data;
	}
	
	public void setData(List<MovieBoxOffice> data) {
		this.data = data;
	}
}
