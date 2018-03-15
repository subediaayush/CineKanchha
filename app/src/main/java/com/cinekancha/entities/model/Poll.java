package com.cinekancha.entities.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class Poll {
	long id;
	private String question;
	private int answer;
	
	private List<String> options = new ArrayList<>();
	
	public String getQuestion() {
		return question;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public List<String> getOptions() {
		return options;
	}
	
	public long getId() {
		return id;
	}
}
