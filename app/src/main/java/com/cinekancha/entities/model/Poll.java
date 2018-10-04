package com.cinekancha.entities.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poll {

	@SerializedName("data")
	@Expose
	private List<PollData> data = null;
	@SerializedName("links")
	@Expose
	private PageLink links;
	@SerializedName("meta")
	@Expose
	private Meta meta;

	public List<PollData> getData() {
		return data;
	}

	public void setData(List<PollData> data) {
		this.data = data;
	}

	public PageLink getLinks() {
		return links;
	}

	public void setLinks(PageLink links) {
		this.links = links;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}