package com.cinekancha.adapters.base;

import android.view.View;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeItemHolder extends BaseViewHolder {
	
	public HomeItemHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
	}
	
	@Override
	public int[] getClickViewIdList() {
		return new int[0];
	}
}