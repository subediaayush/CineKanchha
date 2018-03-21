package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Troll;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTrollViewModel extends AndroidViewModel {
	
	private List<Troll> mTrolls;
	
	public CineTrollViewModel(@NonNull Application application) {
		super(application);
	}
	
	public List<Troll> getTrolls() {
		return mTrolls;
	}
	
	public void setArticles(List<Troll> trolls) {
		this.mTrolls = trolls;
	}
}
