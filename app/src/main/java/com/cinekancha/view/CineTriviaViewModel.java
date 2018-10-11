package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.TriviaData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTriviaViewModel extends AndroidViewModel {
	
	private List<TriviaData> mTriviaData;
	
	public CineTriviaViewModel(@NonNull Application application) {
		super(application);
	}
	
	public List<TriviaData> getTrivias() {
		return mTriviaData;
	}
	
	public void setArticles(List<TriviaData> triviaData) {
		this.mTriviaData = triviaData;
	}
}
