package com.cinekancha.view;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.HomeData;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineHomeViewModel extends AndroidViewModel {
	
	private HomeData mHomeData;
	
	public CineHomeViewModel(@NonNull Application application) {
		super(application);
	}
	
	public HomeData getHomeData() {
		return mHomeData;
	}
	
	public void setHomeData(HomeData homeData) {
		this.mHomeData = homeData;
	}
}
