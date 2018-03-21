package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Trivia;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTriviaViewModel extends AndroidViewModel {
	
	private List<Trivia> mTrivias;
	
	public CineTriviaViewModel(@NonNull Application application) {
		super(application);
	}
	
	public List<Trivia> getTrivias() {
		return mTrivias;
	}
	
	public void setArticles(List<Trivia> trivias) {
		this.mTrivias = trivias;
	}
}
