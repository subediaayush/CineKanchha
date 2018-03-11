package com.cinekancha.home;

import android.view.View;

import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public abstract class HomeItemHolder extends BaseViewHolder {
	
	public HomeItemHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
	}
	
	@Override
	public int[] getClickViewIdList() {
		return new int[0];
	}
}