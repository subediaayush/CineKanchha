package com.cinekancha.adapters;

import android.view.View;

import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.adapters.base.HomeItemHolder;
import com.cinekancha.entities.model.HomeData;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeDataAdapter extends BaseRecyclerAdapter<HomeItemHolder>{
	private HomeData mData;
	
	@Override
	public HomeItemHolder onCreateView(int viewType, View view) {
		return null;
	}
	
	@Override
	public int[] getLayoutsForViewType() {
		return new int[0];
	}
	
	@Override
	protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
	
	}
	
	@Override
	public int getItemCount() {
		return 0;
	}
	
	
	public void setHomeData(HomeData data) {
		this.mData = data;
		notifyDataSetChanged();
	}
}
